
function openForm() {
    document.getElementById('form-container').style.display = 'block';
    document.getElementById('overlay').style.display = 'block';
}

function previewImage(event) {
    const reader = new FileReader();
    reader.onload = function() {
        const preview = document.getElementById('image-preview');
        preview.src = reader.result;
        preview.style.display = 'block';
    }
    reader.readAsDataURL(event.target.files[0]);
}

window.onclick = function(event) {
    const formContainer = document.getElementById('form-container');
    const overlay = document.getElementById('overlay');
    if (event.target === overlay) {
        formContainer.style.display = 'none';
        overlay.style.display = 'none';
    }
}