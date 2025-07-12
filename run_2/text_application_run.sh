#!/bin/bash

# Start the server GUI in a new terminal
gnome-terminal -- bash -c "cd /home/janushan/code/server_sockets && java GUI_S.GUI_S; exec bash"

# Start the server in a new terminal
gnome-terminal -- bash -c "cd /home/janushan/code/server_sockets && java server.Server; exec bash"

# Start the first client in a new terminal
gnome-terminal -- bash -c "cd /home/janushan/code/server_sockets && java client.Client; exec bash"

# Start the second client in a new terminal
gnome-terminal -- bash -c "cd /home/janushan/code/server_sockets && java client.Client; exec bash"