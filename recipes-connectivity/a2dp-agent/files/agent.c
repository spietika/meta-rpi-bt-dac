#include <glib.h>
#include <gio/gio.h>
#include <stdio.h>

GMainLoop *loop;
GDBusConnection *con;
#define AGENT_PATH  "/org/bluez/a2dp"

static void bluez_agent_method_call(GDBusConnection *conn,
                    const gchar *sender,
                    const gchar *path,
                    const gchar *interface,
                    const gchar *method,
                    GVariant *params,
                    GDBusMethodInvocation *invocation,
                    void *userdata)
{

    char *opath;
    char *uuid;
    GVariant *p= g_dbus_method_invocation_get_parameters(invocation);

    g_print("Agent method call: %s.%s()\n", interface, method);
    
    if(!strcmp(method, "AuthorizeService")) {
        g_variant_get(params, "(os)", &opath, &uuid);
        g_print("UUID=%s\n", uuid);

        if(!strcmp(uuid, "0000110d-0000-1000-8000-00805f9b34fb")) {
            g_print("Accepting service\n");
            g_dbus_method_invocation_return_value(invocation, NULL);
        }
        else {
            g_print("Rejecting service\n");
            g_dbus_method_invocation_return_dbus_error(invocation, "org.bluez.Error.Rejected", "Not supported");
        }
    }
}

static const GDBusInterfaceVTable agent_method_table = {
    .method_call = bluez_agent_method_call,
};

int bluez_register_agent(GDBusConnection *con)
{
    GError *error = NULL;
    guint id = 0;
    GDBusNodeInfo *info = NULL;

    static const gchar bluez_agent_introspection_xml[] =
        "<node name='/org/bluez/a2dp'>"
        "   <interface name='org.bluez.Agent1'>"
        "       <method name='AuthorizeService'>"
        "           <arg type='o' name='device' direction='in' />"
        "           <arg type='s' name='uuid' direction='in' />"
        "       </method>"
        "   </interface>"
        "</node>";

    info = g_dbus_node_info_new_for_xml(bluez_agent_introspection_xml, &error);
    if(error) {
        g_printerr("Unable to create node: %s\n", error->message);
        g_clear_error(&error);
        return 0;
    }

    id = g_dbus_connection_register_object(con, 
            AGENT_PATH,
            info->interfaces[0],
            &agent_method_table,
            NULL, NULL, &error);
    g_dbus_node_info_unref(info);

    return id;
}

static int bluez_agent_call_method(const gchar *method, GVariant *param)
{
    GVariant *result;
    GError *error = NULL;

    result = g_dbus_connection_call_sync(con,
            "org.bluez",
            "/org/bluez",
            "org.bluez.AgentManager1",
            method,
            param,
            NULL,
            G_DBUS_CALL_FLAGS_NONE,
            -1,
            NULL,
            &error);
    if(error != NULL) {
        g_print("Register %s: %s\n", AGENT_PATH, error->message);
        return 1;
    }

    g_variant_unref(result);
    return 0;
}

static int bluez_register_autopair_agent(void)
{
    int rc;

    rc = bluez_agent_call_method("RegisterAgent", g_variant_new("(os)", AGENT_PATH, "NoInputNoOutput"));
    if(rc)
        return 1;

    rc = bluez_agent_call_method("RequestDefaultAgent", g_variant_new("(o)", AGENT_PATH));
    if(rc) {
        bluez_agent_call_method("UnregisterAgent", g_variant_new("(o)", AGENT_PATH));
        return 1;
    }

    return 0;
}


static void cleanup_handler(int signo)
{
    if (signo == SIGINT) {
        g_print("received SIGINT\n");
        g_main_loop_quit(loop);
    }
}

int main()
{
    int id;
    int rc;

    if(signal(SIGINT, cleanup_handler) == SIG_ERR)
        g_print("can't catch SIGINT\n");

    con = g_bus_get_sync(G_BUS_TYPE_SYSTEM, NULL, NULL);
    if(con == NULL) {
        g_print("Not able to get connection to system bus\n");
        return 1;
    }

    loop = g_main_loop_new(NULL, FALSE);

    id = bluez_register_agent(con);
    if(id == 0)
        goto fail;

    rc = bluez_register_autopair_agent();
    if(rc) {
        g_print("Not able to register default autopair agent\n");
        goto fail;
    }

    g_main_loop_run(loop);

fail:
    g_dbus_connection_unregister_object(con, id);
    g_object_unref(con);
    return 0;
}
