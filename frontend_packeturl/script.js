const form = document.getElementById('shorten-form');
const longUrlInput = document.getElementById('long-url');
const resultDiv = document.getElementById('result');
const errorDiv = document.getElementById('error');
const shortUrlLink = document.getElementById('short-url');
const copyBtn = document.getElementById('copy-btn');

// Update with your actual backend base URL if needed
const API_BASE_URL = 'http://localhost:8081/api';

form.addEventListener('submit', async (e) => {
    e.preventDefault();
    errorDiv.classList.add('hidden');
    resultDiv.classList.add('hidden');

    const longUrl = longUrlInput.value.trim();
    if (!longUrl) return;

    try {
        // POST request to /api/shorten
        const response = await fetch(`${API_BASE_URL}/shorten?url=` + encodeURIComponent(longUrl), {
            method: 'POST'
        });

        if (!response.ok) {
            throw new Error('Could not shorten the URL');
        }

        const shortCode = await response.text();
        const shortURL = window.location.origin + `/api/${shortCode}`;

        shortUrlLink.href = shortURL;
        shortUrlLink.textContent = shortURL;
        resultDiv.classList.remove('hidden');
    } catch (err) {
        errorDiv.textContent = err.message;
        errorDiv.classList.remove('hidden');
    }
});

copyBtn.addEventListener('click', () => {
    navigator.clipboard.writeText(shortUrlLink.textContent).then(() => {
        copyBtn.textContent = "Copied!";
        setTimeout(() => copyBtn.textContent = "Copy", 1200);
    });
});