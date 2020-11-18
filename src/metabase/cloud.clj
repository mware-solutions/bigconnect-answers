(ns metabase.cloud
  (:require
    [clojure.string :as str]
    [clojure.tools.logging :as log]
    [toucan.db :as db]
    [metabase.core :as mbc]
    [metabase.models.user :as user :refer [User]]
    [metabase.setup :as setup]
    [metabase.public-settings :as public-settings]
    [metabase.config :as config]
    [metabase.integrations.ldap :as ldap]))

(defn setup-site [tenant-id]
  (public-settings/application-name "BigConnect Answers")
  (public-settings/site-name "BigConnect Answers")
  (public-settings/anon-tracking-enabled false)
  (public-settings/check-for-updates false)
  (public-settings/enable-query-caching true)
  (public-settings/enable-public-sharing false)
  (public-settings/start-of-week "monday")
  (public-settings/admin-email "admin@localhost")
  (public-settings/custom-formatting
    {
     "type/Temporal" {
                      "date_style" "D MMMM, YYYY",
                      "time_style" "k:mm"
                      },
     "type/Currency" {
                      "currency" "EUR"
                      }
     }
    )

  (when (config/config-str :mb-cloud)
    (public-settings/cloud-environment true)
    (public-settings/site-url (str "https://answers-" tenant-id ".cloud.bigconnect.io"))
    (public-settings/redirect-all-requests-to-https false)
    (public-settings/enable-embedding true)
    (public-settings/embedding-app-origin "SAMEORIGIN"))

  (when (config/config-str :mb-ldap-enabled)
    (ldap/ldap-enabled true)
    (ldap/ldap-host (config/config-str :mb-ldap-host))
    (ldap/ldap-port (config/config-str :mb-ldap-port))
    (ldap/ldap-bind-dn (config/config-str :mb-ldap-bind-dn))
    (ldap/ldap-password (config/config-str :mb-ldap-bind-password))
    (ldap/ldap-user-base (config/config-str :mb-ldap-user-base))
    (ldap/ldap-user-filter (config/config-str :mb-ldap-user-filter))
    (ldap/ldap-group-sync true)
    (ldap/ldap-group-base (config/config-str :mb-ldap-group-base))
    (ldap/ldap-group-mappings { (eval (config/config-str :mb-ldap-admin-group-dn)) [2] }))

  (setup/clear-token!))

(defn setup-first-user []
  (let [new-user (db/insert! User
                             :email "admin@answers.example.com"
                             :first_name "System"
                             :last_name "Administrator"
                             :password (str (java.util.UUID/randomUUID))
                             :is_superuser true)]
    (user/set-password! (:id new-user) "asdqwe123")))

(defn init-tenant []
  (let [tenant (config/config-str :tenant-id)]
    (when-not tenant (throw (Exception. (str "No TENANT_ID provided for tenant initialization"))))
    (log/infof "Setting up tenant: %s" tenant)
    (mbc/init!)
    (setup-site tenant)
    (setup-first-user)))
