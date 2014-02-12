BRID
====

Brainscorch Remote Image Display

A simple Client/Server application, which allows the client to send images to the server for display.

The server can be started on a small linux distribution connected to an information screen in order to display images in fullscreen without physical proximity to the server itself.

Roadmap:
 
      - Implement settings for fitting the picture (scaling, cropping, keep aspect ratio, etc.)
          - Positioning
               - Position           := <verticalPosition><horizontalPosition>
               - verticalPosition   := T, C, B
               - horizontalPosition := L, C, R
          - Scaling
               - Factor: e.g.   50% = 0.5
                              150%  = 1.5
               - Horizontal Fitting = HFIT
               - Vertical Fitting   = VFIT
               - Total Filling (ignoring aspect ratio)
                                    = TFIT
DONE  - Implement possibility to upload and persist multiple images to the server.
           - Extend communication protocol:
               - E#<hash>  Client asks server is image with has already exists and
                           is not sending it again, if it does.
               - LIST      Client asks for a list of saved images on the server and
                           gets a list of hashes, descriptions, etc. (no images)
               - L#<hash>  Client asks the server to load an image with a specific hash.
      - Implement possibility to script display options (images, duration, fitting, text, etc.)
           - e.g.:     -------------------------------------------------------------------------
                       | ID    | origName        | size       | scale   |  position  | time(s) |
                       -------------------------------------------------------------------------
                       | 174   | awesome.jpg     | 1920x1080  | 1.4     |  LC        | 300     |
                       | 202   | okay.jpg        |  800x700   | HFIT    |  CC        |  60     |
                       |  84   | woho.jpg        | 3000x2000  | 0.9     |  RT        | 180     |
                       -------------------------------------------------------------------------
      - Implement persistency to the server, making it able to load last configuration.
           - Send these configurations on connect to the client in order to alter them. (see protocol)
      - Implement security options like password, allowed IP addresses
      - Reduce the amount of ports needed.
