# PhotoApp
Sample Android app which displays photos from Flikr api, loads in girds on an endless scrollable list. On click of any item/photo displays the full image along with the title associated with it.

* Displays a grid of Images in a list
* Loads a JSON list from an endpoind and change the page of images by changing the query parameter page=X (X being the page of images to return)
* Displays the images in a grid and is scrollable. The list should handle any number of images and the images.
* When an image cell is tapped, a new screen should come up with that image full screen with the title at the bottom of screen
* The list should support pull down to refresh

Libraries used for the app implementation :

* RecyclerView with the StaggeredGridLayoutManager to display the grid of image results
* GSON library to streamline the parsing of JSON data
* RxJava and RetroFit lib to handle the request and response
* Picasso for image rendering.
