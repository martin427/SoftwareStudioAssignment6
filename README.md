# Software Studio Assignment 6

## Notes
+ You will have to import the libraries on your own. All the libraries are provided on iLMS.
+ Even you followed the design TA shown in the video, you still have to explain the control of the program in the next section.

## Explanation of the Design

我們的程式分為三個部分：Main、Character與MainApplet，其中Main是TA已經寫好的。 

Character：為TA提供的STAR WARS中的角色，各項資料由MainApplet讀入，並轉換成小球。

MainApplet:主要會使用到的功能都在這個class完成滑鼠移動、鍵盤輸入等等。 也利用了一些ANI的技巧讓整個流程更加流暢。


Example:
### Operation
1.點擊ADD ALL的按鈕：可以將一次將所有的角色全部拉入大圈，並連線。

2.點擊CLEAR的按鈕：可以將所有的角色放回原位。!

3.滑鼠移到角色上的時候可以顯示名字。

4.也可以藉由單一拖拉的方式，拉到圓圈中

5.可以借由鍵盤上的1~7數字鍵決定集數，但我也另外新增了一個"NEXT EPOSIDE"的鈕，可以一集一集往後分析

### Visualization
將畫面美化，主要以STAR WARS 的主要色調 黑、金來搭配。黑色的背景象徵星空。

覺得單存的"第一集" "第二集"....太單調了 把它改成了真正的電影名，並且將字體改成具有科技感的字型，讓人有身歷奇境的感覺。
