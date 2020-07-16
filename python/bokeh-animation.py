import numpy as np
from bokeh.plotting import figure, show, gridplot, vplot, hplot, curdoc
from bokeh.io import output_notebook
from bokeh.client import push_session
from bokeh.core.state import State as new

# This is where the actual coding begins.
b = np.random.rand(300, 3)
xlist = b[:, 1]
ylist = b[:, 2]

# create a plot and style its properties.  Change chart title here.
p = figure(title='PEG_PLGA15k_F68_R2_P81', title_text_font_size='13pt',
       x_range=(min(xlist), max(xlist)), y_range=(min(ylist), max(ylist)),)

# add a text renderer to out plot (no data yet)
r = p.line(x=[], y=[], line_width=3, color='navy')

session = push_session(curdoc())

i = 0
ds = r.data_source


# create a callback that will add a number in a random location
def callback():
    global i
    ds.data['x'].append(xlist[i])
    ds.data['y'].append(ylist[i])
    ds.trigger('data', ds.data, ds.data)
    if i < xlist.shape[0] - 1:
        i = i + 1
    else:
        new.reset()

# Adds a new data point every 67 ms.  Change at user's discretion.
curdoc().add_periodic_callback(callback, 67)

session.show()

session.loop_until_closed()
