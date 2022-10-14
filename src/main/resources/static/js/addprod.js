const fileAddButton = document.querySelector(".add-button");
const fileInput = document.querySelector(".file-input");
const submitButton = document.querySelector(".submit-button");

let productImageFiles = new Array();


fileAddButton.onclick = () => {
    fileInput.click();
}

fileInput.onchange = () => {
    const formData = new FormData(document.querySelector("form"));

    let changeFlag = false;
    formData.forEach((value) => {
        if(value.size != 0) {
            productImageFiles.push(value);
           changeFlag = true;
        }
    });
    if(changeFlag){
        getImagePreview();
        fileInput.value = null; //file input을 왜 비워줌??
        console.log(productImageFiles);
    }
    
}

function getImagePreview() {
    const productImages = document.querySelector(".product-images");

    productImages.innerHTML = "";
                    //자바스크립트 forEach 함수의 파라미터는 요소, index 그리고 현재 map메서드를 호출한 배열이다.
                    //보통 요소와 index까지 사용하고 배열은 잘 사용하지 않는다. 
        productImageFiles.forEach((file, i) => {
        
        const reader = new FileReader();

        reader.onload = (e) => {
            productImages.innerHTML += `
                <div class="img-box">
                    <i class="fa-solid fa-xmark"></i>
                    <img class="product-img" src="${e.target.result}">
                </div>
            `;

            const deleteButton = document.querySelectorAll(".fa-xmark");
            
            deleteButton.forEach((xbutton, index) => {
                xbutton.onclick = () => {
                    if(confirm("상품 이미지를 지우시겠습니까?")) {
                        productImageFiles.splice(index, 1);
                        console.log(productImageFiles);
                        getImagePreview();
                    }
                };
            })

        }
        //setTimeOut - 
        setTimeout(() => {reader.readAsDataURL(file)}, i * 100);
        
        
    });
}

submitButton.onclick = () => {
    const productInputs = document.querySelectorAll(".product-input");

    //텅빈 formData 생성 가능
    let formData = new FormData();

    formData.append("category",productInputs[0].value);
    formData.append("name",productInputs[1].value);
    formData.append("price",productInputs[2].value);
    formData.append("color",productInputs[3].value);
    formData.append("size",productInputs[4].value);
    formData.append("infoSimple",productInputs[5].value);
    formData.append("infoDetail",productInputs[6].value);
    formData.append("infoOption",productInputs[7].value);
    formData.append("infoManagement",productInputs[8].value);
    formData.append("infoShipping",productInputs[9].value);


    productImageFiles.forEach((file) => {
        formData.append("files",file);
    })
    request(formData);
}

function request(formData){
    $.ajax({
        async: false,
        type: "post",
        url: "/api/admin/product",
        enctype: "multipart/form-data",
        contentType: false,
        processData: false,
        data: formData,
        dataType: "json",
        succcess: (response) =>{
            alert("상품 등록 완료")
            console.log(response)
        },
        error: (error)=>{
            alert("상품 등록 실패")
            console.log(error)
        }
        

    })
}