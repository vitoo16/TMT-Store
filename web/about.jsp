<%-- 
    Author     : vietth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>TMT Store</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">

        <!-- CSS 
        ========================= -->

        <!-- Plugins CSS -->
        <link rel="stylesheet" href="assets/css/plugins.css">

        <!-- Main Style CSS -->
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body>

        <!-- Main Wrapper Start -->
        <!--Offcanvas menu area start-->
        <div class="off_canvars_overlay"></div>
        <jsp:include page="layout/menu.jsp"/>
        <!--breadcrumbs area start-->
        <div class="breadcrumbs_area other_bread">
            <div class="container">   
                <div class="row">
                    <div class="col-12">
                        <div class="breadcrumb_content">
                            <ul>
                                <li><a href="index.jsp">Trang chủ</a></li>
                                <li>/</li>
                                <li>Giới thiệu</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>         
        </div>
        <!--breadcrumbs area end-->

        <!--about section area -->
        <div class="about_section">
            <div class="container">  
                <div class="row">
                    <div class="col-lg-6 col-md-12 ">
                        <div class="about_content">
                            <h1>PRJ301 - SE1845 PROJECT:</h1>
                            <h1>TMT Store</h1>
                            <p>Project TMT Store được xây dựng với mục đích xây dựng một Ecommerce Website có đầy đủ tính năng để phục vụ người dùng.</p>

                            <p><b>Dự án được thực hiện bởi:</b><br>
                                Nguyễn Ngọc Minh - SE182009<br>Mạc Viết Thông - SE181966
                            </p>
                            <p><b><i>Dự án mang tính xây dựng, tăng trải nghiệm cho sinh viên, vui lòng không thương mại hóa ! </i></b></p>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-12">
                        <div class="about_thumb">
                            <img src="assets/img/about/about.jpg" alt="">
                        </div>

                    </div>
                </div>     
            </div>
            <!--about section end-->

            <jsp:include page="layout/footer.jsp"/>

            <!-- JS
            ============================================ -->

            <!-- Plugins JS -->
            <script src="assets/js/plugins.js"></script>

            <!-- Main JS -->
            <script src="assets/js/main.js"></script>



    </body>

</html>