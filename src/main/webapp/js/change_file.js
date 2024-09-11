function openForm() {
    document.getElementById('form-container').style.display = 'block';
}

function previewImage(event) {
    isOpen = true;
    const reader = new FileReader();
    reader.onload = function() {
        const preview = document.getElementById('image-preview');
        preview.src = reader.result;
        preview.style.display = 'block';
    }
    reader.readAsDataURL(event.target.files[0]);
    isOpen = false;
}

window.onclick = function(event) {
    const formContainer = document.getElementById('form-container');
    if (event.target !== formContainer && !formContainer.contains(event.target)) {
        formContainer.style.display = 'none';
    }
}