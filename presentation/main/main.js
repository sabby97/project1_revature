window.onload = getAllEvents();
/* 
    When the logout button is clicked
*/
$('#logoutBtn').on('click', function(){
    localStorage.removeItem("employeeId");
    window.location.href = "C:/Users/jaska/Documents/Revature/Projects/Project1/presentation/login/login.html";
    return;
});


/* 
    Reimbursment Form
*/

$('#formSubmit').click(modalChecker); //when submit is clicked

async function modalChecker(){

    const data = extractReimForm();
    //console.log(data);
    const url = `http://localhost:7000/employee/${localStorage.getItem("employeeId")}/event`;
    
    const httpResponse = await fetch(url, {
        method: "POST",
        body: JSON.stringify(data)
    });

    const body = await httpResponse.json();

    if(httpResponse.status != 200){
        window.alert("Could not add Reimbursment");
        return;
    }

    if(body && body != {}){
        window.alert("success");
        location.reload();
        //console.log(body);
    }
    
    $('#formModal').modal('hide');
}

function extractReimForm(){
    const name = $('#nameInput');
    const eventDate = $('#eventDateInput');
    const location = $('#locationInput');
    const description = $('#descriptionInput');
    const cost = $('#costInput');
    const eventType = $('#eventTypeInput');
    const gradeFormat = $('#gradeFormatInput');
    const letterGrade = $('#letterGradeInput')
    const justification = $('#justificationInput');

    // employee from the local storage
    const employeeIden = localStorage.getItem("employeeId");

    let gradingFormat = (gradeFormat.val() === "2") ? letterGrade.val() : gradeFormat.val();

    const data = {
        eventId : 1,
        employeeId : {employeeId : Number(employeeIden)},
        employeeName : name.val(),
        eventDate: Date.parse(eventDate.val()),
        eventLocation : location.val(),
        eventDescription : description.val(),
        eventCost : Number(cost.val()),
        eventType : {typeId : Number(eventType.val())},
        gradeFormat : {gradeId : Number(gradingFormat)},
        eventCurrStatus : {statusId : 1},
        eventLastStatus : {statusId : 1},
        eventSubmissionDate : Date.now()
    }

    name.val("");
    eventDate.val("");
    location.val("");
    description.val("");
    cost.val("");
    eventType.val("");
    gradeFormat.val("");
    letterGrade.val("");
    justification.val("");

    return data;
}


/* 
Getting all Events for the Role
*/

let eventsList = null;

async function getAllEvents(){
    const url = `http://localhost:7000/employee/${localStorage.getItem("employeeId")}`;

    const httpResponse = await fetch(url);

    const body = await httpResponse.json();
    eventsList = body;
    //console.log(eventsList);

    for (let i=0; i<eventsList.length; i++){
        let employeeName = eventsList[i].employeeName;
        let eventDate = eventsList[i].eventDate;
        let eventType = eventsList[i].eventType.typeName;
        let amount = eventsList[i].eventCost;
        
        popHelper(i,employeeName,eventDate,eventType,amount);

    }
}


function popHelper(a,b,c,d,e,f){
    const tableBody = document.getElementById("reimbursmentTable").getElementsByTagName('tbody')[0];

    //inserting Rows
    const newRow = tableBody.insertRow();
    //inserting cells within the row
    const cell1 = newRow.insertCell();
    const cell2 = newRow.insertCell();
    const cell3 = newRow.insertCell();
    const cell4 = newRow.insertCell();
    const cell5 = newRow.insertCell();
    const cell6 = newRow.insertCell();

    //Ading Information to the cell
    cell1.appendChild(document.createTextNode(a));
    cell2.appendChild(document.createTextNode(b));
    let dateTemp = new Date(c).toLocaleDateString("en-US");
    cell3.appendChild(document.createTextNode(dateTemp));
    cell4.appendChild(document.createTextNode(d));
    cell5.appendChild(document.createTextNode(e));
    
    const myButton = document.createElement("button");
    myButton.innerHTML = "Review";
    myButton.setAttribute("class", "btn btn-primary btn-lg");
    myButton.setAttribute("data-bs-toggle","modal");
    myButton.setAttribute("data-bs-target", "#adminModal");
    myButton.setAttribute("data-whatever", String(a));
    cell6.appendChild(myButton);
    
}

/* 
    for updating events
*/

$(document).ready(function(){
    $('#adminModal').on('shown.bs.modal', function(event){
        let myButt = $(event.relatedTarget);
        let index = Number(myButt.data('whatever'));
        //console.log(index);
        let modal = $(this);

        let dateTemp = new Date(eventsList[index].eventDate).toLocaleDateString("en-US");
        
        $('#adminField1').text('Employee Name: '+ eventsList[index].employeeId.employeeName);
        $('#adminField2').text('Event Date: '+ dateTemp);
        $('#adminField3').text('Event Location: '+ eventsList[index].eventLocation);
        $('#adminField4').text('Event Amount: '+ eventsList[index].eventCost);
        $('#adminField5').text('Event Type: '+ eventsList[index].eventType.typeName);
        $('#adminField6').text('Grade Format: '+ eventsList[index].gradeFormat.gradeType + ', Cutoff Grade: ' +eventsList[index].gradeFormat.gradeCutoff);
        $('#adminField7').text('Status: '+ eventsList[index].eventCurrStatus.statusMsg);
        if(eventsList[index].eventGrade){
            $('#finalGradeField').text('EVENT GRADE: '+ eventsList[index].eventGrade);
        }
        if(eventsList[index].adminMsg){
            $('#adminField8').text('Admin Message: '+ eventsList[index].adminMsg);
        }

        //logic for employee 

        if(Number(localStorage.getItem("employeeId")) === eventsList[index].employeeId.employeeId){ // if the current user owns the event
            
            modal.find('.modal-footer').show();
            $('#adminMsgDiv').show();
            $('#adminRequested').show();
            
            let currStatus = eventsList[index].eventCurrStatus.statusId;
            let currGrade  = eventsList[index].eventGrade;
            let infoNeeded = false;
            let isGraded = true;

            //check if the request has been approved or rejected
            if(currStatus < 6){
                $('#adminApprove').hide();
            }
            if(currStatus === 6 || currStatus === 7 || currStatus === 14){
                modal.find('.modal-footer').hide();
            }
            
            $('#adminMsgDiv').hide();
            $('#adminRequested').hide();
            $('#adminReject').html("Cancel Reimbursement");
            $('#adminApprove').html("Update Request");

            if(currStatus === 8 || currStatus === 9 || currStatus === 11 ){ // if aditional information is requested by the admin
                $('#adminMsgDiv').show(); 
                $('#adminMsgDiv').find('.form-label').html("Provide Information Requested by the admin! : ")
                infoNeeded = true;
            }
            
            
            if(currStatus === 4){ // if there is no final grade show the form 
                $('#adminApprove').show();
                isGraded = false;
                let input1 = '<label for="finalGrade">Select you final Grade</label>';
                if(eventsList[index].gradeFormat.gradeId === 1){ //pass or fail
                    input1 += '<select name="finalGrade" id="finalGrade"><option value="Pass">Pass</option><option value="Fail">Fail</option></select>';
                }
                else if(eventsList[index].gradeFormat.gradeId === 7){ // is a presentation
                    input1 += '<select name="finalGrade" id="finalGrade"><option value="yes">Presented</option><option value="no">Did not present</option></select>';
                }
                else{
                    input1 += '<select name="finalGrade" id="finalGrade"><option value="A">A</option><option value="B">B</option><option value="C">C</option><option value="D">D</option><option value="F">F</option></select>';
                }

                $('#manipulationDiv').append(input1);
            }
            


            $('#adminReject').on('click', async function(){ // to cancel the reimbursement
                let url = `http://localhost:7000/employee/${localStorage.getItem("employeeId")}/event/${eventsList[index].eventId}`;
                let httpResponse = await fetch(url,{
                    method : 'DELETE'
                });
                let body = await httpResponse.json();

                if(httpResponse.status != 200){  // error handling
                    console.log("could not delete the event");
                    console.log(httpResponse.statusText);
                    $('#finalGrade').remove();
                    modal.modal('hide');
                    $('#manipulationDiv').clear();
                    return;
                }
                if(body && body != {}){
                    window.alert("Cancelling you request"); // success
                    //console.log(body);
                    location.reload();
                }
                $('#manipulationDiv').clear();
            });


            $('#adminApprove').on('click', async function(){ // submit info
                let data = eventsList[index];
                if(!isGraded){ // if the event grade need to be unpdated
                    //console.log("should not be in here");
                    data.eventGrade = $('#finalGrade').val();
                    data.eventLastStatus.statusId = data.eventCurrStatus.statusId;
                    data.eventLastStatus.statusMsg = data.eventCurrStatus.statusMsg;
                }
                if(infoNeeded){ // if the msg needs to updated
                    //console.log("in here");
                    data.adminMsg = $('#adminMsgInput').val();
                    //console.log(data.eventLastStatus);
                    //console.log(data.eventCurrStatus);
                }

                console.log(data);

                let url = `http://localhost:7000/employee/${localStorage.getItem("employeeId")}/event/${data.eventId}`;
                let httpResponse = await fetch(url,{
                    method : 'PUT',
                    body : JSON.stringify(data)
                });

                let eventResponse = await httpResponse.json();
                if(httpResponse.status == 200 && eventResponse != null && eventResponse != {}){
                    location.reload();
                }
                
                else{
                    console.log("Error occured please contact support");
                    console.log(httpResponse.statusText);
                    //console.log(eventResponse);
                    $('#manipulationDiv').clear();
                    modal.modal('hide');
                    return;
                }           
            });
            
        }
        // if the current user is Benco
        else if(eventsList[index].eventCurrStatus.statusId === 3 || eventsList[index].eventCurrStatus.statusId === 5){
            //make sure they buttons pop up
            modal.find('.modal-footer').show();
            $('#adminMsgDiv').show();
            $('#adminRequested').show();

            //set the html
            $('#adminReject').html("Reject Request");
            $('#adminRequested').html("Request Additional information");
            $('#adminApprove').html("Approve Request");

            //set the request dropdown
            $('#adminMsgDiv').find('.form-label').html("Ask for additional information : ");
            let input = '<label for="msgFor">Request a message from:</label><select name="msgFor" id="msgFor"><option value="11" selected>Employee</option><option value="12" >Supervisor</option> <option value="13" >Department Head</option>';
            $('#adminMsgDiv').append(input);

            $('#adminReject').on('click', async function (){
                let data2 = eventsList[index];
                data2.adminMsg = $('#adminMsgInput').val();
                data2.eventLastStatus.statusId = data2.eventCurrStatus.statusId;
                data2.eventLastStatus.statusMsg = data2.eventCurrStatus.statusMsg;
                data2.eventCurrStatus.statusId = 14;

                let url = `http://localhost:7000/employee/${localStorage.getItem("employeeId")}/event/${data2.eventId}`;
                let httpResponse = await fetch(url,{
                    method : 'PUT',
                    body : JSON.stringify(data2)
                });

                let eventResponse = await httpResponse.json();
                if(httpResponse.status == 200 && eventResponse != null && eventResponse != {}){
                    location.reload();
                }
                
                else{
                    console.log("Error occured please contact support");
                    console.log(httpResponse.statusText);
                    //console.log(eventResponse);
                    $('#manipulationDiv').clear();
                    modal.modal('hide');
                    return;
                }           

            });

            $('#adminRequested').on('click', async function(){
                let data = eventsList[index];
                data.adminMsg = $('#adminMsgInput').val();
                data.eventCurrStatus.statusId = data.eventLastStatus.statusId;
                data.eventCurrStatus.statusMsg = data.eventLastStatus.statusMsg;
                data.eventCurrStatus.statusId = Number($('#msgFor').val());

                let url = `http://localhost:7000/employee/${localStorage.getItem("employeeId")}/event/${data.eventId}`;
                let httpResponse = await fetch(url,{
                    method : 'PUT',
                    body : JSON.stringify(data)
                });

                let eventResponse = await httpResponse.json();

                if(httpResponse.status == 200 && eventResponse != null && eventResponse != {}){
                    location.reload();
                }
                
                else{
                    console.log("Error occured please contact support");
                    console.log(httpResponse.statusText);
                    //console.log(eventResponse);
                    $('#manipulationDiv').clear();
                    modal.modal('hide');
                    return;
                }

            });

            $('#adminApprove').on('click', async function(){
                let data = eventsList[index];

                data.eventLastStatus.statusId = data.eventCurrStatus.statusId;
                data.eventLastStatus.statusMsg = data.eventCurrStatus.statusMsg;
                

                let url = `http://localhost:7000/employee/${localStorage.getItem("employeeId")}/event/${data.eventId}`;
                let httpResponse = await fetch(url,{
                    method : 'PUT',
                    body : JSON.stringify(data)
                });

                let eventResponse = await httpResponse.json();

                if(httpResponse.status == 200 && eventResponse != null && eventResponse != {}){
                    location.reload();
                }
                
                else{
                    console.log("Error occured please contact support");
                    console.log(httpResponse.statusText);
                    //console.log(eventResponse);
                    $('#manipulationDiv').clear();
                    modal.modal('hide');
                    return;
                }

            });

        }
        //other admins
        else{
            //make sure they buttons pop up
            modal.find('.modal-footer').show();
            $('#adminMsgDiv').show();
            $('#adminRequested').show();

            //set the html
            $('#adminReject').html("Reject Request");
            $('#adminRequested').html("Request Additional information");
            $('#adminApprove').html("Approve Request");

            // first let's check, if higher admin have been requested additional information from user
            let currStatus = eventsList[index].eventCurrStatus.statusId;
            let isRequested = false;

            if(currStatus === 10 || currStatus === 12 || currStatus === 13){
                isRequested = true;
                $('#adminRequested').hide();
                $('#adminMsgDiv').find('.form-label').html("Provide Information Requested by the admin! : ");
                $('#adminApprove').html("Update Request");
            }

            //if information is not asked by the higher admin, so the user can ask for additional information
            if(!isRequested){
                $('#adminMsgDiv').find('.form-label').html("Ask for additional information : ")
                let input2 = '<label for="msgFor">Request a message from:</label>';
                if(eventsList[index].eventCurrStatus.statusId === 1){ // this is for the supervisor view
                    input2 += '<select name="msgFor" id="msgFor"><option value="8" selected>Employee</option>';
                }
                else{
                    input2 += '<select name="msgFor" id="msgFor"><option value="9" selected>Employee</option><option value="10" selected>Supervisor</option>';
                }
                $('#adminMsgDiv').append(input2);
            }

           
            $('#adminReject').on('click', async function (){
                let data2 = eventsList[index];
                data2.adminMsg = $('#adminMsgInput').val();
                data2.eventLastStatus.statusId = data2.eventCurrStatus.statusId;
                data2.eventLastStatus.statusMsg = data2.eventCurrStatus.statusMsg;
                data2.eventCurrStatus.statusId = 14;

                let url = `http://localhost:7000/employee/${localStorage.getItem("employeeId")}/event/${data2.eventId}`;
                let httpResponse = await fetch(url,{
                    method : 'PUT',
                    body : JSON.stringify(data2)
                });

                let eventResponse = await httpResponse.json();
                if(httpResponse.status == 200 && eventResponse != null && eventResponse != {}){
                    location.reload();
                }
                
                else{
                    console.log("Error occured please contact support");
                    console.log(httpResponse.statusText);
                    //console.log(eventResponse);
                    $('#manipulationDiv').clear();
                    modal.modal('hide');
                    return;
                }           

            });

            $('#adminApprove').on('click', async function(){
                let data = eventsList[index];
                if(isRequested){  
                    data.adminMsg = $('#adminMsgInput').val();
                }
                else{
                    console.log("should not be here");
                    data.eventLastStatus.statusId = data.eventCurrStatus.statusId;
                    data.eventLastStatus.statusMsg = data.eventCurrStatus.statusMsg;
                }

                
                let url = `http://localhost:7000/employee/${localStorage.getItem("employeeId")}/event/${data.eventId}`;
                let httpResponse = await fetch(url,{
                    method : 'PUT',
                    body : JSON.stringify(data)
                });

                let eventResponse = await httpResponse.json();

                if(httpResponse.status == 200 && eventResponse != null && eventResponse != {}){
                    location.reload();
                }
                
                else{
                    console.log("Error occured please contact support");
                    console.log(httpResponse.statusText);
                    //console.log(eventResponse);
                    $('#manipulationDiv').clear();
                    modal.modal('hide');
                    return;
                }

            });

            $('#adminRequested').on('click', async function(){
                let data = eventsList[index];
                data.adminMsg = $('#adminMsgInput').val();
                data.eventLastStatus.statusId = data.eventCurrStatus.statusId;
                data.eventLastStatus.statusMsg = data.eventCurrStatus.statusMsg;
                //console.log("not changing the status correctly: " + data.eventLastStatus.statusId);
                data.eventCurrStatus.statusId = Number($('#msgFor').val());

                let url = `http://localhost:7000/employee/${localStorage.getItem("employeeId")}/event/${data.eventId}`;
                let httpResponse = await fetch(url,{
                    method : 'PUT',
                    body : JSON.stringify(data)
                });

                let eventResponse = await httpResponse.json();

                if(httpResponse.status == 200 && eventResponse != null && eventResponse != {}){
                    location.reload();
                }
                
                else{
                    console.log("Error occured please contact support");
                    console.log(httpResponse.statusText);
                    //console.log(eventResponse);
                    $('#manipulationDiv').clear();
                    modal.modal('hide');
                    return;
                }

            });

        }
        
    });

});

//dropdown for the ReimbursmentForm/GradeType=Letter Grade
$("#gradeFormatInput").on('change', function (){
    const el = $(this);
    if(el.val() === "2"){
        $("#letterGradeInput").attr("hidden",false);
    }
    else{
        $("#letterGradeInput").attr("hidden",true);
    }
});