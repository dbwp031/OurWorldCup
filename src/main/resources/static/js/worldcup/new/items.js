$(document).ready(function () {
    $('#item-table').on('click', '.delete-row-btn', function () {
        let row = $(this).closest('tr');
        let id = row.data('id');

        $.ajax({
            url: '/api/v1/worldcup/item/' + id,
            type: 'DELETE',
            success: function (data) {
                if (data.success) {
                    row.remove();
                } else {
                    alert('Failed to delete data');
                }
            },
        })
    });
});