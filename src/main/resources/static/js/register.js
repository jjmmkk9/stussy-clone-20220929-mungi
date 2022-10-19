const registerButton = document.querySelector(".login-button");
const registerInputs = document.querySelectorAll(".login-input");
<<<<<<< HEAD
=======

for(let i = 0; i < registerInputs.length; i++){
    registerInputs[i].onkeyup = (e) => {
        if(e == 13) { //e = window.event
            if (i != 3) {
                registerInputs[i + 1].focus();
            } else {
                registerButton.click();
            }
        }
    }
}
registerButton.onclick = () =>{
>>>>>>> af23d26 (AccountRepository, mappers, Service,)


for(let i = 0; i < registerInputs.length; i++) {
    registerInputs[i].onkeyup = (e) => {
        if(e.keyCode === 13){
            if(i < 3) {
                registerInputs[i + 1].focus();
            }else {
                registerButton.click();
            }
        }
    }
}
registerButton.onclick = () =>{
    //객체 만들기 만들어서 json.stringify 해서 json 으로 만들어버려
    let registerInfo = {
        lastName: registerInputs[0].value,
        firstName: registerInputs[1].value,
        email: registerInputs[2].value,
        password: registerInputs[3].value
    }
    $.ajax({
        async: false,
        type: "post",
        url: "/api/account/register",
        contentType: "application/json",
        data: JSON.stringify(registerInfo),
        dataType: "json",
        success: (response) => {
<<<<<<< HEAD
            //href아니고 replace 인 이유??? : replace 하면 이전 페이지가 사라짐
            location.replace("/account/login");
        },
        error: (error) => {
            console.log(error); //에러 객체 전체 안에서 responseJSON 안에 data 를 가져와줌(콘솔창에 에러 객체 확인해보면 경로 있음)
            validationError(error.responseJSON.data);
=======
          console.log(response);
          //href아니고 replace 인 이유??? : replace 하면 이전 페이지가 사라짐
          location.replace("/account/login");
        },
        error: (error) => {
          console.log(error); //에러 객체 전체 안에서 responseJSON 안에 data 를 가져와줌(콘솔창에 에러 객체 확인해보면 경로 있음)
          validationError(error.responseJSON.data);
>>>>>>> af23d26 (AccountRepository, mappers, Service,)
        }
    });
}
<<<<<<< HEAD
function validationError(error) {
    const accountErrors = document.querySelector(".account-errors");
    const accountErrorList = accountErrors.querySelector("ul");
    //에러 객체가 몇개인지 list 로 가져와(entries가 뭐지? : "list를 가져오는데 키랑 밸류가 묶여진 상태로 가져옴")
    const errorValues = Object.values(error);
    accountErrorList.innerHTML = "";
    //values에서 하나 하나 꺼내면서 <li>에 표시해주기
    errorValues.forEach((value) => {
        accountErrorList.innerHTML += `
            <li>${value}</li>
        `;
    });
    accountErrors.classList.remove("errors-invisible");
}

=======
function validationError(error){
    const accountErrors = document.querySelector(".account-errors");
    const accountErrorList = accountErrors.querySelector("ul");
         //에러 객체가 몇개인지 list 로 가져와(entries가 뭐지? : "list를 가져오는데 키랑 밸류가 묶여진 상태로 가져옴")
    const errorValues = Object.values(error);

    accountErrorList.innerHTML = "";

    //values에서 하나 하나 꺼내면서 <li>에 표시해주기
    errorValues.forEach((value) =>{
        accountErrorList.innerHTML += `
        <li>${value}</li>
        `;
    })


    accountErrors.classList.remove("errors-invisible");
}
>>>>>>> af23d26 (AccountRepository, mappers, Service,)
