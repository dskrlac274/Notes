from __future__ import print_function

import os
import tkinter
from tkinter import *

import enchant
import threading

import tk
import pygame
from flask import Flask, request
from flask_cors import CORS
from pygame import mixer

app = Flask(__name__)

cors = CORS(app)
app.config['CORS_HEADERS'] = 'Access-Control-Allow-Origin'


@app.route('/test', methods=['POST'])
def test():
    dictionary = enchant.request_pwl_dict("/home/daniel/Desktop/PyWo/demo/src/main/resources/static/js/index.txt")
    if request.data == " ":
        return " "
    else:
        wordToCheck = str(request.data)[1:].replace("'", "")
        wordToCheck = wordToCheck.replace(" ", "")
        return str(dictionary.check(wordToCheck))


@app.route('/testAll', methods=['POST'])
def testAll():
    dictionary = enchant.request_pwl_dict("/home/daniel/Desktop/PyWo/demo/src/main/resources/static/js/index.txt")
    text = str(request.data)[1:].replace("'", "")
    text = text.split()
    values = []
    for x in text:
        values.append(str(dictionary.check(x)))
    return str(dict(zip(text, values)))


@app.route('/music', methods=['POST'])
def music():
    tk_main()
    return "Music player --> Turn off"


def Play():
    song = songs_list.get(ACTIVE)
    song = '/home/daniel/Music/' + song
    mixer.music.load(song)
    mixer.music.play()


# to pause the song
def Pause():
    mixer.music.pause()


# to stop the  song
def Stop():
    mixer.music.stop()
    songs_list.selection_clear(ACTIVE)


# to resume the song
def Resume():
    mixer.music.unpause()


def tk_main():
    # TkApp(root)
    root = Tk()
    pygame.mixer.init()
    root.title('Music player')
    global songs_list
    songs_list = tkinter.Listbox(root, selectmode=tkinter.SINGLE, bg="#37a3eb", fg="white", font=('arial', 16),
                                 height=3, width=24)

    play_button = tkinter.Button(root, text="Play", width=7, command=Play)
    pause_button = tkinter.Button(root, text="Pause", width=7, command=Pause)
    stop_button = tkinter.Button(root, text="Stop", width=7, command= Stop)
    resume_button = tkinter.Button(root, text="Resume", width=7, command=Resume)


    songs_list.grid(columnspan=9)
    play_button.grid(row=1, column=0)
    pause_button.grid(row=1, column=1)
    stop_button.grid(row=1, column=2)
    resume_button.grid(row=1, column=3)

    my_menu = tkinter.Menu(root)
    root.config(menu=my_menu)
    add_song_menu = tkinter.Menu(my_menu)
    my_menu.add_cascade(label="Menu", menu=add_song_menu)
    add_song_menu.add_command(label="Add songs")
    os.chdir("/home/daniel/Music/")
    songtracks = os.listdir()
    for track in songtracks:
        songs_list.insert(END, track)

    root.mainloop()


if __name__ == "__main__":
    app.run(host="localhost", port=8081)
