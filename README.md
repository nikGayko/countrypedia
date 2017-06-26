# Countrypedia
Android applicatoin providing information about countries from https://restcountries.eu/

# grouping elements
For grouping elements used two recycler adapters were separators is an element type of header. They bind on view_head_country.xml.

# svg problem
After a few fail attempts to load svg images with the help of BitmapFactory it become a problem. To solve this problem was used androidsvg-1.2.1.jar. This lib contains class SVG for svg images and can return Drawable object. This returned object i put into cards(elements of recycler view). But it work veryVeryVery slooooow. To opimize EecyclerView scrolling performance was used Bitmap.

# features, but not bugs
User can search by capital, country, and both. To realize this feature was writen class CheckBoxGroup in package view. This class keep links to checkbox in ckeckboxgroup. If only one checkbox checked, it will automaticly disable this last checkbox.

To open map view user should click this:
![action bar](https://github.com/nikGayko/countrypedia/blob/master/actionbar(map).png)

To see information on the map user need to tap on a land. After that he will see a card on the bottom of the screen with country name, country capital and flag. To see more details about country tap on this card.


P.S 
It will be pleased for me if you left your remarks and suggestions in issues
Thank you:)
