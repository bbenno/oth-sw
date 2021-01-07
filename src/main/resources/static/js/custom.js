function submitFormsWithCtrlEnter() {
    document
        .querySelectorAll('form')
        .forEach(function (form) {
            form.addEventListener('keydown', function (event) {
                if (event.ctrlKey && event.key === "Enter") {
                    let submitEvent = document.createEvent('HTMLEvents');
                    submitEvent.initEvent('submit');
                    form.dispatchEvent(submitEvent);
                }
            });
        });
}

function ready(fn) {
    if (document.readyState !== 'loading'){
        fn();
    } else {
        document.addEventListener('DOMContentLoaded', fn);
    }
}

ready(submitFormsWithCtrlEnter);
