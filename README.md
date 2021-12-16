# TravelApp44 <img src="https://i.imgur.com/qZdVDQV.png" alt="TravelApp44" width="100" align="right"/> 
Đây là ứng dụng du lịch thông minh cung cấp thông tin về các địa chỉ du lịch, khách sạn, quán ăn
<img src="https://i.imgur.com/23vYbwc.jpg" width="300"/>

## Mục lục

[Giới thiệu](#gioithieu)

[I. Tổng quan dự án](#tq)
- [1. Mục đích](#md)
- [2. Phạm vi](#pv)
  
[II. Cơ sở lí thuyết](#cosolithuyet)
- [1. Firebase](#firebase)
- [2. Firebase Tools](#fbt)
  - [2.1. Firebase Authentication](#fbau)
  - [2.2. Realtime Database](#rdb)
  - [2.3. Cloud Firestore](#cfs)
  - [2.4. Cloud Storage for Firebase](#csf)
    
[III. Phân tích và thiết kế hệ thống](#phantichthietke)
  - [1. Mô hình MVVM](#mvvm)
  - [2. Fragment](#fragment)
  - [3. Sử dụng tài khoản người dùng](#chucnanght)
    - [3.1. Chức năng đăng nhập](#dn)
    - [3.2. Chức năng đăng kí](#dk)
    - [3.3. Chức năng xem địa điểm](#dd)
    - [3.4. Chức năng xem chi tiết địa điểm](#ctdd)
    - [3.5. Chức năng xem khách sạn](#ks)
    - [3.6. Chức năng xem chi tiết khách sạn](#ctks)
    - [3.7. Chức năng xem quán ăn](#qa)
    - [3.8. Chức năng xem review](#review)
    - [3.9. Chức năng tìm kiếm](#tk)
    - [3.10. Chức năng xem người dùng](#user)
    - [3.11. Chức năng đổi mật khẩu](#change)
    - [3.12. Chức năng tìm địa chỉ](#dc)
  - [4. Sử dụng tài khoản admin](#admin)
    - [4.1. Chức năng thêm và sửa](#add)
    - [4.2. Chức năng xóa](#remove)

[IV. Kết luận và hướng phát triển](#klhpt)
- [1. Kết luận](#kl)
- [2. Hướng phát triển](#hpt)

[Tài liệu tham khảo](#tltk)


===========================
<a name="gioithieu"></a>
# Giới thiệu
Kinh tế phát triển, kéo theo đó là chất lượng đời sống ngày càng cao. Và, nhu cầu vui chơi, giải trí là không thể thiếu, cùng với đó là nhu cầu du lịch cũng ngày càng tăng ở khách nội địa. Tuy nhiên, khi muốn đi du lịch thì một số người gặp phải các vấn đề như: chưa chọn được nơi đi du lịch, không biết khách sạn nào tốt, không biết ở địa điểm đó có những món ăn gì nổi tiếng. Từ đó, nhóm đã tìm hiểu và lập trình một ứng dụng android giúp cho việc đi du lịch của mọi người được thuận lợi hơn.

<a name="tq"></a>
## I.Tổng quan dự án

<a name="md"></a>
### 1. Mục đích
- Xây dựng app android nhằm hỗ trợ những người đi du lịch tìm kiếm những địa danh nổi tiếng trên cả nước
- Bên cạnh đó giúp họ tìm được những khách sạn thoải mái, phù hợp với từng người
- Hỗ trợ người dung tìm kiếm thức ăn nổi tiếng ở từng địa điểm
- Có thể định vị địa điểm cho người sử dụng

<a name="pv"></a>
### 2. Phạm vi
- Lập trình di động: sử dụng ngôn ngữ java
- Hệ cơ sở dữ liệu: Firebase


<a name="cosolithuyet"></a>
## II. Cơ sở lí thuyết

<a name="firebase"></a>
### 1. Firebase
`Firebase` là một nền tảng để phát triển ứng dụng di động và trang web, bao gồm các API đơn giản và mạnh mẽ mà không cần backend hay server.
Lợi ích của `Firebase`: giúp các lập trình viên rút ngắn thời gian triển khai và mở rộng quy mô của ứng dụng mà họ đang phát triển.
`Firebase` là dịch vụ cơ sở dữ liệu hoạt động trên nền tảng đám mây – cloud. Kèm theo đó là hệ thống máy chủ cực kỳ mạnh mẽ của Google. Chức năng chính là giúp người dùng lập trình ứng dụng bằng cách đơn giản hóa các thao tác với cơ sở dữ liệu
<img src="https://i.imgur.com/1WlEZ0X.png" width="600">

<a name="fbt"></a>
### 2. Firebase Tools

<a name="fbau"></a>
### 2.1. Firebase Authentication
`Firebase Authentication` là chức năng dùng để xác thực người dùng bằng Password, số điện thoại hoặc tài khoản Google, Facebook hay Twitter, v.v. ... `Firebase Authentication` giúp thực hiện việc chia sẻ ID giữa các ứng dụng, giúp người dùng dễ dàng tiếp cận sản phẩm hơn.
<img src="https://i.imgur.com/A2sDmx0.jpg" width="600">

<a name="rdb"></a>
### 2.2. Realtime Database
`Realtime Database` là một cơ sở dữ liệu NoSQL được lưu trữ đám mây cho phép bạn lưu trữ và đồng bộ dữ liệu. Dữ liệu được lưu trữ dưới dạng JSON và được đồng bộ hóa theo thời gian thực cho mọi máy kết nối.
<img src="https://i.imgur.com/d8AxhYl.png" width="600">

<a name="cfs"></a>
### 2.3. Cloud Firebase
`Cloud Firestore` là một cơ sở dữ liệu NoQuery được lưu trữ trên đám mây mà các ứng dụng IOS, Android, Web có thể truy cập trực tiếp thông qua SDK. `Cloud Firestore` cũng có sẵn trong Node. js, Java, Python và Go SDKs, REST và RPC APIs.
<img src="https://i.imgur.com/lLWSHnu.jpg" width="600">

<a name="csf"></a>
### 2.4. Cloud Storage for Firebase
`Cloud Storage for Firebase` là không gian lưu trữ dữ liệu. Dữ liệu ở đây không có giới hạn nào cả. Bạn có thể chứa bất kì các (loại) tập tin nào mà bạn muốn, như ảnh, nhạc, video hoặc các tập tin text, zip hay thậm chí là một tập tin với kiểu dữ liệu của riêng bạn thiết kế.
<img src="https://i.imgur.com/BltRgue.jpg" width="600">

<a name="phantichthietke"></a>
## III. Phân tích và thiết kế hệ thống

<a name="mvvm"></a>
## 1. Mô hình MVVM
`MVVM` là một trong những mô hình kiến trúc được rất nhiều lập trình viên yêu thích sử dụng. Mô hình này sinh ra dành cho các ứng dụng sử dụng ngôn ngữ XML để định nghĩa giao diện ứng dụng như: Windows Phone 8.0, 8.1, Silverlight, Windows RT,... `MVVM` cũng mang lại rất nhiều lợi ích nổi bật.
<img src="https://i.imgur.com/UkqmJ3i.png" width="600">

<a name="fragment"></a>
## 2. Fragment
`Fragment` là một thành phần android độc lập, được sử dụng bởi một activity, giống như một sub-activity. `Fragment` có vòng đời và giao diện riêng. Các `Fragment` thường có một file java đi kèm với file giao diện xml. 
Các `fragment` không có file giao diện xml thường được gọi là headless `fragment`
<img src="https://i.imgur.com/LoQUjPg.png" width="600">

<a name="chucnanght"></a>
## 3. Sử dụng tài khoản người dùng

<a name="dn"></a>
## 3.1. Chức năng đăng nhập
<img src="https://i.imgur.com/LPX3pDL.jpg" width="350">

<a name="dk"></a>
## 3.2. Chức năng đăng kí
<img src="https://i.imgur.com/xBqfBK2.jpg" width="350">

<a name="dd"></a>
## 3.3. Chức năng xem địa điểm 
<img src="https://i.imgur.com/PQg5Tg8.jpg" width="350">

<a name="ctdd"></a>
## 3.4. Chức năng xem chi tiết địa điểm 
<img src="https://i.imgur.com/z1v4F36.jpg" width="350">

<a name="ks"></a>
## 3.5. Chức năng xem khách sạn 
<img src="https://i.imgur.com/bk2HJ49.jpg" width="350">

<a name="ctks"></a>
## 3.6. Chức năng xem chi tiết khách sạn 
<img src="https://i.imgur.com/Uvn0tQ5.jpg" width="350">

<a name="qa"></a>
## 3.7. Chức năng xem quán ăn 
<img src="https://i.imgur.com/odUA5HP.jpg" width="350">

<a name="review"></a>
## 3.8. Chức năng xem review 
<img src="https://i.imgur.com/RilSosH.jpg" width="350">

<a name="tk"></a>
## 3.9. Chức năng tìm kiếm 
<img src="https://i.imgur.com/2HspBKo.jpg" width="350">

<a name="user"></a>
## 3.10. Chức năng xem review 
<img src="https://i.imgur.com/s3pQhfr.jpg" width="350">


<a name="change"></a>
## 3.11. Chức năng thay đổi mật khẩu 
<img src="https://i.imgur.com/pjTGxGy.jpg" width="350">

<a name="dc"></a>
## 3.12. Chức năng tìm địa chỉ 
<img src="https://media.giphy.com/media/nz1jWJtTwmMjLC2dt1/giphy.gif" width="350"/>

<a name="admin"></a>
## 4. Sử dụng tài khoản admin
<img src="https://i.imgur.com/4OKF2aS.jpg" width="350"/>

<a name="add"></a>
## 4. chức năng thêm và sửa
<img src="https://i.imgur.com/2xUQNYI.jpg" width="350"/>

<a name="remove"></a>
## 4. Chức năng xóa
<img src="https://i.imgur.com/4091hh1.jpg" width="350"/>

<a name="klhpt"></a>
## IV. Kết luận và hướng phát triển
  
<a name="kl"></a>
## 1. Kết luận 
Ứng dụng dễ sử dụng, giao điện bắt mắt, gần gũi.
Quản lí chặt chẽ phân quyền người sử dụng hợp lí.
Hoàn thành tương đối phân tích thiết kế hệ thống.
Thiết kế ứng dụng có các chức năng cơ bản đáp ứng được yêu cầu sử dụng của người dùng.
Song bên cạnh đó nếu có thêm thời gian để phát triển và hoàn thiện hơn thì ứng dụng sẽ rất hữu ích đối với nhu cầu của người du lịch. 
Hạn chế: Ứng dụng có tính chuyên nghiệp chưa cao, chưa giải quyết được trọn vẹn những vấn đề nảy sinh trong quá trình sử dụng, chưa đạt tính thẩm mỹ cao

<a name="hpt"></a>
## 2. Hướng phát triển
Cải thiện giao diện đẹp mắt, thân thiện, dễ sử dụng hơn, phù hợp với mọi lứa tuổi.
Cải thiện việc truy cập, tìm kiếm dữ liệu tốc độ nhanh, độ chính xác cao.
Nâng cấp thêm chức năng chỉ đường, đặt phòng khách sạn, book tour du lịch,..
Nâng cấp hệ thống đăng nhập (đăng nhập bằng Facebook, Google, ..).

===============================
<a name="tltk"></a>
# Tài liệu tham khảo
[Mô hình MVVM:](https://viblo.asia/p/gioi-thieu-ve-mo-hinh-mvvm-trong-ung-dung-android-m68Z0pvjZkG) https://viblo.asia/p/gioi-thieu-ve-mo-hinh-mvvm-trong-ung-dung-android-m68Z0pvjZkG

[Firebase:](https://firebase.google.com/docs/guides) https://firebase.google.com/docs/guides

[Fragment:](https://viblo.asia/p/su-dung-fragment-trong-android-4P856a2alY3) https://viblo.asia/p/su-dung-fragment-trong-android-4P856a2alY3






  
