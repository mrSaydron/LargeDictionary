var ajaxLearnWord = "/ajax/learnword";
var learnWordTable;
var searchWord;

function checkWord(checkBox, id) {
    var enabled = checkBox.is(":checked");
}

function checkWords() {
    var checked = $('#learnWordTable').find("input:checked");
    var checkedId = [];
    for(i=0;i<checked.length;i++){
        checkedId.push(checked[i].value);
    }
    return checkedId;
}

function add() {
    $("#modalTitle").html("Добавить слово");
    var form = $("#editForm");
    form.find(":input").val("");
    $("#editWord").modal();
}

$(function () {
    learnWordTable = $('#learnWordTable').DataTable({
        "ajax": {
            "url": ajaxLearnWord,
            "dataSrc": ""
        },
        "dom": 'lrtip',
        "paging": false,
        "info": true,
        "columns": [
            {
                "orderable": false,
                "defaultContent": "",
                "render": function (data, type, row) {
                    return "<input type='checkbox' onclick='checkWord($(this)," + row.id + ");' value= '" + row.id + "'/>";
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
    var value = this.value;
    learnWordTable.search( value ).draw();
    console.log("up");
});

$("#deleteWords").click(function () {
    var deleteWordsId = checkWords();
    $.ajax({
        url: ajaxLearnWord,
        type: "POST",
        data: {id:deleteWordsId}
    }).done(function () {
        for(i=0;i<learnWordTable.rows().data().length;) {
            if(deleteWordsId.some(function (t) { return learnWordTable.row(i).data().id.toString() === t })) {
                learnWordTable.row(i).remove();
            } else i++;
        }
        learnWordTable.draw();
    });
});

$("#word").blur(function() {
    $.ajax({
        url: ajaxLearnWord + "/word",
        type: "POST",
        data: {word: $("#word").val()}
    }).done(function (data) {
        searchWord = data;
        var form = $("#editForm");
        if(data.translations != null) {
            $('#editForm input[name = "id"]').val(searchWord.learnWordId);
            $('#transcription').val(searchWord.transcription);
            $('#translation').val(searchWord.translations);
            if(searchWord.userEdit && searchWord.cammonTranslations != null) {
                $('#user-edited').css('display','block');
                $('#alredy-use').css('display','none');
                $('#delete-button').css('display','inline');
            } else {
                $('#user-edited').css('display','none');
                $('#alredy-use').css('display','block');
                $('#delete-button').css('display','inline');
            }
        } else if(searchWord.cammonTranslations != null) {
            $('#transcription').val(searchWord.commonTranscription);
            $('#translation').val(searchWord.commonTranslations);
            $('#user-edited').css('display','none');
            $('#alredy-use').css('display','none');
            $('#delete-button').css('display','none');
        }
    });
});

$('#return-transmition').click(function() {
    $('#transcription').val(searchWord.commonTranscription);
    $('#translation').val(searchWord.commonTranslations);
    $('#user-edited').css('display','none');
    $('#alredy-use').css('display','none');
});

$('#delete-button').click(function () {
    var m = [searchWord.learnWordId];
    $.ajax({
        url: ajaxLearnWord,
        type: "POST",
        data: {id:m}
    }).done(function () {
        for(i=0;i<learnWordTable.rows().data().length; i++) {
            if(learnWordTable.row(i).data().id.toString() == searchWord.learnWordId) {
                learnWordTable.row(i).remove();
                break;
            }
        }
        $('#editWord').modal('hide');
        learnWordTable.draw();
    });
});

$('#save-button').click(function () {
   $.ajax({
       url: ajaxLearnWord + "/addWord",
       type: "POST",
       data: $('#editForm').serialize()
   }).done(function (data) {
       learnWordTable.row.add(data);
       $('#editWord').modal('hide');
       learnWordTable.draw();
   });
});