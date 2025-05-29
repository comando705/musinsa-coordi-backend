/**
 * Global JavaScript functions for the application
 */

// Show loading spinner
function showSpinner() {
    document.querySelector('.spinner-container').style.display = 'flex';
}

// Hide loading spinner
function hideSpinner() {
    document.querySelector('.spinner-container').style.display = 'none';
}

// Show alert message
function showAlert(message, type = 'success') {
    const alertContainer = document.getElementById('alertContainer');
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;
    alertContainer.appendChild(alertDiv);

    // Auto dismiss after 5 seconds
    setTimeout(() => {
        alertDiv.classList.remove('show');
        setTimeout(() => alertDiv.remove(), 500);
    }, 5000);
}

// Generic function to handle API errors
function handleApiError(error) {
    console.error('API Error:', error);
    if (error.response && error.response.data && error.response.data.error) {
        showAlert(error.response.data.error.message, 'danger');
    } else {
        showAlert('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.', 'danger');
    }
    hideSpinner();
}

// Generic function to make API requests
async function apiRequest(url, method = 'GET', data = null) {
    showSpinner();

    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        }
    };

    if (data && (method === 'POST' || method === 'PUT')) {
        options.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(url, options);
        const responseData = await response.json();

        if (!response.ok) {
            throw { response: { data: responseData } };
        }

        hideSpinner();
        return responseData;
    } catch (error) {
        handleApiError(error);
        throw error;
    }
}

// Document ready function
document.addEventListener('DOMContentLoaded', function() {
    // Add event listeners for modals to reset form on close
    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => {
        modal.addEventListener('hidden.bs.modal', function() {
            const form = this.querySelector('form');
            if (form) form.reset();
        });
    });
});