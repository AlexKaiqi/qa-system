function hasValidToken() {
    var token = localStorage.getItem("token");
    if (token === null) return false;
    var formData = {token: token};
    $.ajax({
        type: 'POST', // define the type of HTTP verb we want to use (POST for our form)
        url: '/user/token', // the url where we want to POST
        data: formData, // our data object
        dataType: 'json', // what type of data do we expect back from the server
        encode: true,
        async: false,
        success: function(result) {
            return result.success === true;
        }
    })
};