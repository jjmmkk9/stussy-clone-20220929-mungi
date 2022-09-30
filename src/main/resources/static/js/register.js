
const registerButton = document.querySelector(".login-button");
registerButton.onclick = () =>{
    const registerInputs = document.querySelectorAll("login-input");

    //객체 만들기 만들어서 json.stringify 해서 json 으로 만들어버려
    let registerInfo = {
        lastName: registerInputs[0].value,
        firstName:registerInputs[1].value,
        email:registerInputs[2].value,
        password: registerInputs[3].value,
    }
    $.ajax({
      async: false,
      type: "POST",
      url: "/api/account/register",
      contentType: "application/json",
        data: JSON.stringify(registerInfo),
        dataType: "json",
        success: (response) => {
          console.log(response);
        },
        error: (error) => {
          console.log(error);
        }

    });
}
