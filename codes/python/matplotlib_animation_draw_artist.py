#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
main.py
from https://gist.github.com/iwatake2222/2d2a39ac062e6b823822412ef5f1c1ba
"""
__author__ = "take-iwiw"
__copyright__ = "Copyright 2017, take-iwiw"
__date__ = "18 Oct 2017"


import logging
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.cm as cm
import pandas as pd
import time
import matplotlib.animation as animation
import threading
import multiprocessing
from multiprocessing import Pool, Array, Pipe
logging.basicConfig(level=logging.DEBUG,
                    format='[%(levelname)s] (%(threadName)-10s) %(message)s',
                    )

CANVAS_WIDTH:  int = 100
CANVAS_HEIGHT: int = 100
SCREEN_NUMBER_X: int = 2
SCREEN_NUMBER_Y: int = 2


# def worker_draw(data_previous_start, data_previous_end):
def worker_draw(conn,):
    fig = plt.figure()
    ax_previous_start = plt.subplot(SCREEN_NUMBER_X, SCREEN_NUMBER_Y, 1)
    ax_previous_end = plt.subplot(SCREEN_NUMBER_X, SCREEN_NUMBER_Y, 2)
    ax_total_start = plt.subplot(SCREEN_NUMBER_X, SCREEN_NUMBER_Y, 3)
    ax_total_end = plt.subplot(SCREEN_NUMBER_X, SCREEN_NUMBER_Y, 4)

    ax_previous_start.pcolor(np.zeros((CANVAS_WIDTH, CANVAS_HEIGHT)))
    ax_previous_end.pcolor(np.zeros((CANVAS_WIDTH, CANVAS_HEIGHT)))
    ax_total_start.pcolor(np.zeros((CANVAS_WIDTH, CANVAS_HEIGHT)))
    ax_total_end.pcolor(np.zeros((CANVAS_WIDTH, CANVAS_HEIGHT)))

    fig.canvas.draw()
    fig.show()

    while True:
        t_start = time.time()
        if conn.poll() is False:
            # time.sleep(1)
            continue
        data = conn.recv()

        heatmap = ax_previous_start.pcolor(data[0])
        ax_previous_start.draw_artist(ax_previous_start.patch)
        ax_previous_start.draw_artist(heatmap)
        fig.canvas.blit(ax_previous_start.bbox)

        heatmap = ax_previous_end.pcolor(data[1])
        ax_previous_end.draw_artist(ax_previous_end.patch)
        ax_previous_end.draw_artist(heatmap)
        fig.canvas.blit(ax_previous_end.bbox)

        heatmap = ax_total_start.pcolor(data[2])
        ax_total_start.draw_artist(ax_total_start.patch)
        ax_total_start.draw_artist(heatmap)
        fig.canvas.blit(ax_total_start.bbox)

        heatmap = ax_total_end.pcolor(data[3])
        ax_total_end.draw_artist(ax_total_end.patch)
        ax_total_end.draw_artist(heatmap)
        fig.canvas.blit(ax_total_end.bbox)

        fig.canvas.flush_events()
        t_end = time.time()
        logging.debug("fps_draw = {0}".format(999 if t_end == t_start else 1/(t_end-t_start)))
    return


def worker_calc(conn,):
    while True:
        t_start = time.time()
        data_previous_start = np.random.rand(CANVAS_WIDTH, CANVAS_HEIGHT)
        data_previous_end = np.random.rand(CANVAS_WIDTH, CANVAS_HEIGHT)
        data_total_start = np.random.rand(CANVAS_WIDTH, CANVAS_HEIGHT)
        data_total_end = np.random.rand(CANVAS_WIDTH, CANVAS_HEIGHT)

        conn.send([data_previous_start, data_previous_end, data_total_start, data_total_end])
        # time.sleep(10)
        t_end = time.time()
        logging.debug("fps_calc = {0}".format(999 if t_end == t_start else 1/(t_end-t_start)))
    return


def main():
    """
    Entry function
    :called when: the program starts
    :param none: no parameter
    :return: none
    :rtype: none
    """
    parent_conn, child_conn = Pipe()

    job_calc = multiprocessing.Process(target=worker_calc, args=(parent_conn,))
    job_calc.start()
    job_draw = multiprocessing.Process(target=worker_draw, args=(child_conn,))
    job_draw.start()

    while True:
        time.sleep(100)


if __name__ == '__main__':
    main()
