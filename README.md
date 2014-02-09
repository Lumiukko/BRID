BRID
====

Brainscorch Remote Image Display

A simple Client/Server application, which allows the client to send images to the server for display.

The server can be started on a small linux distribution connected to an information screen in order to display images in fullscreen without physical proximity to the server itself.

Roadmap:

- Implement settings for fitting the picture (scaling, cropping, keep aspect ratio, etc.)
- Implement possibility to upload multiple images to the server.
- Implement possibility to script display options (images, duration, fitting, text, etc.)
- Implement persistency to the server, making it able to load last configuration.
  - Send these configurations on connect to the client in order to alter them.
- Implement security options like password
- Reduce the amount of ports needed.
