$(document).ready(function() {

});



function getRawCountViaAjax(selected) {


    $.ajax({
        type : "GET",
        url : "/getNoOfRecords?fileName=" + selected,
        timeout : 100000,
        success : function(data) {
            console.log("SUCCESS: ", data);
            displayInfo(data);
        },
        error : function(e) {
            console.log("ERROR: ", e);
            displayInfo(e);
        },
        done : function(e) {
            console.log("DONE");
        }
    });
}


function displayInfo(data) {

    //console.log(data);
    var json = JSON.stringify(data, null, 4);

    if(data.length>0){
        content ='<div class="alert alert-success">No of records: '+data+'</div>';

    }else{
        content = '<div class="alert alert-info">No records</div>';
    }


    $('#contant').html(content);




}
