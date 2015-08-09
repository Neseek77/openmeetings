Usage of layout is nice but it needs more views just for layouting.
In a simple scenario thats okay, but in the openmeetings app itself most of the views are set **absolute** by x and y cause this reduces the total amount of views instancing (=making views dynamically like we do) is time critical .. if you create or destroy too many views at one time the browser hangs and cpu will be at 100%.

  * try to use as less views as possible
  * use at less constraints as possible
  * use $once{ .. } instead of ${ .. } or use setters and getters
  * don't use canvas.setAttribute('myvar','foo') just use canvas.myvar='foo'
  * whenever it is not necessary (it will throw some  events which are useless if you don't have any constraints pointing to 'myvar' and which will cost performance)