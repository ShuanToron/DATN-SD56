/**
 *
 * You can write your JS code here, DO NOT touch the default style file
 * because it will make it harder for you to update.
 *
 */
// Hiện preview ảnh khi thêm sản phẩm
function handleFileSelect(evt) {
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

// Hiện form thêm nhanh loại giày
function popupLoaiGiay() {
    var popup = document.getElementById("formLoaiGiay");
    if (popup.style.display === "block") {
        popup.style.display = "none";
    } else {
        popup.style.display = "block";
    }
}

// Hiện form thêm nhanh thương hiệu
function popupThuongHieu() {
    var popup = document.getElementById("formThuongHieu");
    if (popup.style.display === "block") {
        popup.style.display = "none";
    } else {
        popup.style.display = "block";
    }
}

function popupChatLieu() {
    var popup = document.getElementById("formChatLieu");
    if (popup.style.display === "block") {
        popup.style.display = "none";
    } else {
        popup.style.display = "block";
    }
}

function popupDeGiay() {
    var popup = document.getElementById("formDeGiay");
    if (popup.style.display === "block") {
        popup.style.display = "none";
    } else {
        popup.style.display = "block";
    }
}

function popupChucVu() {
    var popup = document.getElementById("formChucVu");
    if (popup.style.display === "block") {
        popup.style.display = "none";
    } else {
        popup.style.display = "block";
    }
}

"use strict";

