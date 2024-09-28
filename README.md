# Brakesor
Plugin for simulating airbrakes in OpenRocket.
Made for UCF's 2024 NSL rocket.

Contributions and recommendations are welcome, as the plugin's still in progress.

# Installation
Similar to any other OpenRocket plugin.
Drop brakesor.jar into C:\Users\USERNAME\AppData\Roaming\OpenRocket\Plugins\
Alternatively find the roaming folder using %appdata% in file explorer.

# Usage
Once in OpenRocket, edit any simulation, and within the simulation options tab
add the NSL Airbrakes extension under Airbrakes/NSL Airbrakes. In here, 
set your deployment altitude and the exact name of the part (CASE SENSITIVE)
you want to act as your airbrakes.

# Demo
A demo file is included in the repo's main directory. (NSL OpenRocket Demo.ork)

# Limitations
Airbrake mass is not simulated when not deployed. Along with this, airbrake deployment
is instant, and you are limited to OpenRocket's selection of parts for how to represent
your airbrake.

# Sources
Setup for the files and a lot of UI code (see BrakesorConfigurator.java) was based off of examples on the OpenRocket repo at
https://github.com/openrocket/openrocket/blob/unstable/docs/source/user_guide/simulation_extensions.rst#id23

All simulation code is original (this is why recommendations are welcome lol).
