
# Mobile Automation using Appium

Appium là một công cụ mã nguồn mở cho phép tự động hóa kiểm thử các ứng dụng di động trên cả Android và iOS. Nó hỗ trợ kiểm thử các ứng dụng native, hybrid, và mobile web.

### Các đặc điểm chính của Appium:

1. **Đa nền tảng**: Appium có thể kiểm thử ứng dụng trên cả hai nền tảng Android và iOS bằng cách sử dụng cùng một mã kiểm thử, giúp giảm thiểu thời gian và công sức cho việc phát triển và duy trì các bộ kiểm thử.

2. **Không cần sửa đổi ứng dụng**: Appium không yêu cầu mã nguồn của ứng dụng phải thay đổi hoặc cài đặt thêm bất kỳ SDK nào để có thể kiểm thử, giúp giảm thiểu tác động lên ứng dụng gốc.

3. **Sử dụng các ngôn ngữ lập trình phổ biến**: Appium hỗ trợ nhiều ngôn ngữ lập trình như Java, Python, Ruby, JavaScript, C#, PHP, và nhiều ngôn ngữ khác thông qua WebDriver, giúp lập trình viên dễ dàng tích hợp và viết các kiểm thử tự động.

4. **Hỗ trợ kiểm thử đa dạng**: Appium hỗ trợ kiểm thử các loại ứng dụng khác nhau, bao gồm ứng dụng native (ứng dụng được phát triển bằng các công cụ chính thức của nền tảng), ứng dụng hybrid (ứng dụng kết hợp giữa web và native), và ứng dụng mobile web (truy cập thông qua trình duyệt trên thiết bị di động).

### Kiến trúc của Appium:

- **Appium Server**: Là trung tâm của Appium, chịu trách nhiệm nhận các lệnh kiểm thử từ khách hàng (client) và chuyển chúng thành các lệnh tương ứng trên thiết bị di động. Server này có thể được cài đặt và chạy độc lập.
  
- **Appium Client**: Là các thư viện ngôn ngữ lập trình khác nhau mà bạn sử dụng để viết các kịch bản kiểm thử. Các thư viện này sử dụng giao thức WebDriver để gửi các lệnh đến Appium Server.
  
- **Các trình điều khiển (Drivers)**: Appium sử dụng các trình điều khiển khác nhau để giao tiếp với thiết bị và hệ điều hành di động. Ví dụ, UIAutomator2 và Espresso cho Android, và XCUITest cho iOS.

### Quá trình kiểm thử với Appium:

1. **Cài đặt Appium Server**: Bạn có thể cài đặt Appium Server thông qua npm hoặc tải về phiên bản Appium Desktop.
  
2. **Viết kịch bản kiểm thử**: Sử dụng một ngôn ngữ lập trình và Appium Client để viết các kịch bản kiểm thử. Bạn cần định nghĩa các đối tượng UI và hành động muốn thực hiện trên các đối tượng đó.
  
3. **Cấu hình Desired Capabilities**: Xác định các thuộc tính cần thiết để khởi tạo phiên làm việc với Appium, như thông tin thiết bị, hệ điều hành, tên ứng dụng, v.v.
  
4. **Khởi chạy kiểm thử**: Chạy kịch bản kiểm thử và theo dõi kết quả thông qua Appium Server.

### Kiến trúc Mobile Automation using Appium

app

	Application.java: khởi tạo driver với các thông tin cấu hình ở BaseTest.java

common

	- BasePage.java: Parent class của các class trong pageObject package, implement các basic keyword
	- BaseTest.java: Parent class của các class trong testcase package, đọc các tham số đầu vào từ file TestNG và thiết lập các thông tin cấu hình trước khi khởi tạo driver

pageObject: Chứa các class màn hình, nơi cung cấp các service cho các bài test

	- Mainscreen.java
	- TermOfServiceScreen.java
	- TutorialScreen.java

utils

	- Constant.java: Khởi tạo giá trị cho các Constant sử dụng trong project
	- MyUtil.java: Chứa các utilities function cần thiết trong project
	- Log.java: Chứa các function liên quan đến Log
	- ImageCompare.java: Chứa các function so sánh hình ảnh dùng để check GUI
	- OCR.java: Chứa các function hỗ trợ cho việc thực hiện các action lên các image element
	- TestCaseInfoLog.java: Chứa các function hỗ trợ ghi thông tin actual result, expected result của test case vào log
	- XpathResource: Chứa các function hỗ trợ lấy đúng thông tin xpath của các element trong properties file

resources: Chứa các file xpath properties

	- Xpath_Android.properties
	- Xpath_iOS.properties

testcases: Chứa nội dung của các test case script