var ajaxLearnWord = "/ajax/learnword";
var learnWordTable;

$(function () {
    learnWordTable = $('#learnWordTable').DataTable({
        "ajax": {
            "url": ajaxLearnWord,
            "dataSrc": ""
        },
        "searching": false,
        "paging": false,
        "info": true,
        "columns": [
            {
                "orderable": false,
                "defaultContent": "",
                "render": function (data, type, row) {
                    return '<label><input type="checkbox" onclick="checkWord($(this),' + row.id + '"></label>';
                }
            },
            {
                "data": "word"
            },
            {
                "data": "translations"
            },
            {
                "data": "frequency"
            }
        ],
        "order": [
            [
                1,
                "asc"
            ]
        ]
    })

});

$("#searchWord").on("keyup", function (){
    learnWordTable.search( this.value ).draw();
    console.log("up");
});

function checkWord(checkBox, id) {
    var enabled = checkBox.is(":checked");
}