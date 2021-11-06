const loginBtn = document.getElementById("submitLogin");

async function Login(){
    const email = document.getElementById("emailInput");
    const password = document.getElementById("passwordInput");
    const emailValid = /(?=\@).+\./;

    if(!emailValid.test(email.value)){
        email.value = "";
        password.value = "";
        alert("Please enter an valid email");
    }
    else if(password.value === ""){
        alert("Please enter an valid password");
        password.value = "";
        email.value = "";
    }
    else{
        //console.log(`The email is ${email.value} and the password is ${password.value}`);
        const data = {
            employeeEmail : email.value,
            employeePassword : password.value
        };
        const url = "http://localhost:7000/login";

        const httpResponse = await fetch(url, {
            //mode : "no-cors",
            method: "POST",
            body: JSON.stringify(data)
        });

        const body = await httpResponse.json();
        
        if(httpResponse.status == 404){
            window.alert("Credentials not in the database please check your login information and try again")
            password.value = "";
            email.value = "";
            window.location.href = "C:/Users/jaska/Documents/Revature/Projects/Project1/presentation/login/login.html";
            return;
        }

        if(body && (body != {})){
            //console.log(body);
            localStorage.setItem("employeeId",body.employeeId);
            //console.log("Checking Storage "+localStorage.getItem("employeeId"));
            window.location.href = "C:/Users/jaska/Documents/Revature/Projects/Project1/presentation/main/main.html";
            return;
        }
    }

}


loginBtn.addEventListener('click', Login);