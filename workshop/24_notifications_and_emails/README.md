## 24. NOTIFICATION AND EMAILS

Constantly checking a long running pipeline can be frustrating, especially if it fails and you are unaware that it needs attention. To help with this, Bpipe can send notifications about pipeline events via email or XMPP (instant messaging).


To use notifications you need to create a Bpipe configuration file called bpipe.config in the local directory where the pipeline is running. In this file, you can specify various ways to receive notifications, and set filters on which notifications you are to receive. All notifications are configured in a "notifications" block, and each entry therein configures a separate notification. You can configure as many as you want, as long as each one has a different name.

Note: you can place 'global' configuration for notifications in a file in your home directory called ".bpipeconfig". These will be shared by all Bpipe pipelines in any directory, but any local configuration will override global configurations.

see also: https://code.google.com/p/bpipe/wiki/Notifications


Test Dirs:

1. plain PLAIN EMAIL NOTIFICATION
2. html  HTML EMAIL NOTIFICATION
3. gtalk GOOGLETALK NOTIFICATION