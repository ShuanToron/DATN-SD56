/**
 *
 * You can write your JS code here, DO NOT touch the default style file
 * because it will make it harder for you to update.
 * 
 */
function handleFileSelect (evt) {
    // Loop through the FileList and render image files as thumbnails.
    for (const file of evt.target.files) {

        // Render thumbnail.
        const span = document.createElement('span')
        const src = URL.createObjectURL(file)
        span.innerHTML =
            `<img style="height: 75px; border: 1px solid #000; margin: 5px"` +
            `src="${src}" title="${escape(file.name)}">`

        document.getElementById('list').insertBefore(span, null)
    }
}

document.getElementById('files').addEventListener('change', handleFileSelect, false);
"use strict";

