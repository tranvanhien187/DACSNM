package com.example.dacsnm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dacsnm.observe.DataStation;
import com.example.dacsnm.observe.Observer;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements Observer {
    /**
    *   Red turn : 1
    *   Black turn : 2
    **/

    private static final String ipDell = "192.168.1.3";
    private static final String ipMSI = "192.168.1.9";
    private final DataStation dataStation = DataStation.newInstance();
    private ConstraintLayout constraintLayout;
    boolean isMyTurn ;
    static boolean isRed ;
    int last_click = -1;
    boolean over = false;

    private Player mPlayer;

    void restart(View view) {
        if(!isMyTurn) {
            Toast.makeText( getApplicationContext(), "这不是你的回合", Toast.LENGTH_SHORT).show();
            return;
        }
        if(record.size()>1) {
            board = first_board.clone();
            initBoard();
        } else {
            Toast.makeText( getApplicationContext(), "您还没有落子", Toast.LENGTH_SHORT).show();
        }
    }

//    void set_info() {
//        ImageView tv = findViewById(R.id.info);
//        if(my_turn) {
//            tv.setVisibility(View.VISIBLE);
//        } else {
//            tv.setVisibility(View.INVISIBLE);
//        }
//
//    }
    private int[] board = {
            1, 3, 5, 7,16, 8, 6, 4, 2,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 9, 0, 0, 0, 0, 0,10, 0,
            11, 0,12, 0,13, 0,14, 0,15,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            27, 0,28, 0,29, 0,30, 0,31,
            0,25, 0, 0, 0, 0, 0,26, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            17,19,21,23,32,24,22,20,18
    };

    /* CỜ ĐEN
     *  1,2 xe đen
     *  3,4 mã đen
     *  5,6 tịnh đen
     *  7,8 tốt đen
     *  9,10 pháo đen
     *  11,12,13,14,15 tốt đen
     * 16 tướng đen
     *
     * CỜ ĐỎ
     *  17,18 xe đỏ
     *  19,20 mã đỏ
     *  21,22 tượng đỏ
     *  23,24 sĩ đỏ
     *  25,26 pháo đỏ
     *  27,28,29,30,31 tốt đỏ
     *  32 tướng đỏ
     */

    //
    private final int[] first_board = {
            1, 3, 5, 7,16, 8, 6, 4, 2,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 9, 0, 0, 0, 0, 0,10, 0,
            11, 0,12, 0,13, 0,14, 0,15,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            27, 0,28, 0,29, 0,30, 0,31,
            0,25, 0, 0, 0, 0, 0,26, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            17,19,21,23,32,24,22,20,18
    };
    List<int[]> record = new ArrayList<>();

    // Xếp bàn cờ lần đầu
    void initBoard() {
        constraintLayout = findViewById(R.id.board);
        for(int i = 0; i < 90; i++) {
            int chess = board[i];
            // xe đen
            if(chess == 1 || chess == 2)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.b_c);
            // xe đỏ
            if(chess == 17 || chess == 18) {
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.r_c);
            }

            // mã đen
            if(chess == 3 || chess == 4)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.b_m);
            // mã đỏ
            if(chess == 19 || chess == 20)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.r_m);

            // tịnh đen
            if(chess == 5 || chess == 6)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.b_x);
            // tịnh đỏ
            if(chess == 21 || chess == 22)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.r_x);

            // sĩ đen
            if(chess == 7 || chess == 8)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.b_s);
            // sĩ đỏ
            if(chess == 23 || chess == 24)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.r_s);

            // tướng đen
            if(chess == 16)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.b_j);
            // pháo tướng đỏ
            if(chess == 32)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.r_j);

            // pháo đen
            if(chess == 9 || chess == 10)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.b_p);
            // pháo đỏ
            if(chess == 25 || chess == 26)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.r_p);

            // chốt đen
            if(chess == 11 || chess == 12 || chess == 13 || chess == 14 || chess == 15)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.b_z);
            // chốt đỏ
            if(chess == 27 || chess == 28 || chess == 29 || chess == 30 || chess == 31)
                constraintLayout.getChildAt(i).setBackgroundResource(R.drawable.r_z);

            if(chess == 0)
                constraintLayout.getChildAt(i).setBackground(null);
        }
    }

    public void back(View view) {
        this.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Tạo người chơi
        mPlayer = Player.getInstance("","");
        new Thread(mPlayer).start();

        dataStation.registerObserver(this);

        record.add(board.clone());
        initBoard();


    }

    void record_board() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 9; j++) {
                System.out.print(board[i*9+j]);
                System.out.print(", ");
            }
            System.out.println();
        }
    }

    void update_action(int index) {
        record_board();
        TextView AI_action = findViewById(R.id.AI_action);
        TextView player_action = findViewById(R.id.player_action);
        if(isMyTurn) {
            player_action.setText("Player : move"+chess_name(board[index]));
        } else {
            AI_action.setText("AI: move"+chess_name(board[index]));
        }
    }

    String chess_name(int chess) {
        // 车
        if(chess == 1 || chess == 2 || chess == 17 || chess == 18)
            return "车";
        // 马
        if(chess == 3 || chess == 4 || chess == 19 || chess == 20)
            return "马";
        // 象
        if(chess == 5 || chess == 6 || chess == 21 || chess == 22)
            return "象";
        // 士
        if(chess == 7 || chess == 8 || chess == 23 || chess == 24)
            return "士";
        // 将
        if(chess == 16 || chess == 32)
            return "将";
        // 炮
        if(chess == 9 || chess == 10 || chess == 25 || chess == 26)
            return "炮";
        // 兵
        if(chess == 11 || chess == 12 || chess == 13 || chess == 14 || chess == 15 || chess == 27 || chess == 28 || chess == 29 || chess == 30 || chess == 31)
            return "兵";
        return "error";
    }

    boolean canMove(int from_index, int to_index) {
        if(from_index == to_index)
            return false;
        int from_x = from_index%9;  // lấy toạ độ x của vị trí hiện tại
        int from_y = from_index/9;  // lấy toạ độ y của vị trí hiện tại
        int to_x = to_index%9;      // lấy toạ độ x của vị trí muốn tới
        int to_y = to_index/9;      // lấy toạ độ x của vị trí muốn tới
        int from = board[from_index];  //  lấy quân cờ hiện tại được chọn
        int chessMan = board[to_index];      // lấy quân cờ tại điểm đến

        switch(from) {
            case 17:case 18:case 1:case 2: // Xe
                if(from_x != to_x && from_y != to_y) // Điều kiện x không bằng x , y không bằng y thì sẽ không cùng hàng
                    return false;
                // Kiểm tra vật cản trên đường
                if(from_y == to_y) {  // hàng ngang
                    if(from_index < to_index) {  // Từ vị trí hiện tại cho đến vị trí tới nếu có quân khác thì không được đi
                        for(int i = from_index+1; i < to_index; i++)
                            if(board[i] != 0)
                                return false;
                    } else {
                        for(int i = from_index-1; i > to_index; i--)
                            if(board[i] != 0)
                                return false;
                    }
                } else {            // hàng dọc
                    if(from_index < to_index) {
                        for(int i = from_index+9; i < to_index; i+=9)
                            if(board[i] != 0)
                                return false;
                    } else {
                        for(int i = from_index-9; i > to_index; i-=9)
                            if(board[i] != 0)
                                return false;
                    }
                }
                break;
            case 19:case 20:case 3:case 4: // Mã
                // 走日字
                // Đi không đúng quy luật của con mã
                if(!(to_x == from_x+1 && to_y == from_y-2)&&!(to_x == from_x+2 && to_y == from_y-1)&&
                        !(to_x == from_x+2 && to_y == from_y+1)&&!(to_x == from_x+1 && to_y == from_y+2)&&
                        !(to_x == from_x-1 && to_y == from_y+2)&&!(to_x == from_x-2 && to_y == from_y+1)&&
                        !(to_x == from_x-2 && to_y == from_y-1)&&!(to_x == from_x-1 && to_y == from_y-2))
                    return false;
                // 卡马脚
                // Bị cản
                if((to_index == from_index+1-18 && board[from_index-9] != 0) || (to_index == from_index+2-9 && board[from_index+1] != 0) ||
                        (to_index == from_index+2+9 && board[from_index+1] != 0) || (to_index == from_index+1+18 && board[from_index+9] != 0) ||
                        (to_index == from_index-1-18 && board[from_index-9] != 0) || (to_index == from_index-2-9 && board[from_index-1] != 0) ||
                        (to_index == from_index-2+9 && board[from_index-1] != 0) || (to_index == from_index-1+18 && board[from_index+9] != 0)) {
                    return false;
                }
                break;
            case 21:case 22: // tượng đỏ
                // Không thể qua sông
                if(to_index < 45)
                    return false;
                // Đi không đúng quy luật
                if(!(to_x == from_x+2 && to_y == from_y-2)&&!(to_x == from_x+2 && to_y == from_y+2)&&
                        !(to_x == from_x-2 && to_y == from_y+2)&&!(to_x == from_x-2 && to_y == from_y-2))
                    return false;
                // Bị cản
                if((to_index == from_index+2-18 && board[from_index+1-9] != 0) || (to_index == from_index+2+18 && board[from_index+1+9] != 0) ||
                        (to_index == from_index-2-18 && board[from_index-1-9] != 0) || (to_index == from_index-2+18 && board[from_index-1+9] != 0)) {
                    return false;
                }
                break;
            case 5:case 6: // tượng đen
                // Không thể qua sông
                if(to_index > 44)
                    return false;
                // Đi không đúng quy luật
                if(!(to_x == from_x+2 && to_y == from_y-2)&&!(to_x == from_x+2 && to_y == from_y+2)&&
                        !(to_x == from_x-2 && to_y == from_y+2)&&!(to_x == from_x-2 && to_y == from_y-2))
                    return false;
                // Bị cản
                if((to_index == from_index+2-18 && board[from_index+1-9] != 0) || (to_index == from_index+2+18 && board[from_index+1+9] != 0) ||
                        (to_index == from_index-2-18 && board[from_index-1-9] != 0) || (to_index == from_index-2+18 && board[from_index-1+9] != 0)) {
                    return false;
                }
                break;
            case 23: case 24: // sỹ đỏ
                // Không thể rời khỏi cung điện
                if(to_y < 7 || to_x <3 || to_x > 5)
                    return false;
                // Đi không đúng quy luật
                if(!(to_x == from_x+1 && to_y == from_y-1)&&!(to_x == from_x+1 && to_y == from_y+1)&&
                        !(to_x == from_x-1 && to_y == from_y+1)&&!(to_x == from_x-1 && to_y == from_y-1))
                    return false;
                break;
            case 7: case 8: // sỹ đen
                // Không thể rời khỏi cung điện
                if(to_y > 2 || to_x <3 || to_x > 5)
                    return false;
                // Đi không đúng quy luật
                if(!(to_x == from_x+1 && to_y == from_y-1)&&!(to_x == from_x+1 && to_y == from_y+1)&&
                        !(to_x == from_x-1 && to_y == from_y+1)&&!(to_x == from_x-1 && to_y == from_y-1))
                    return false;
                break;
            case 32: // tướng đỏ
                // Trường hợp tướng đối mặt tướng
                if(chessMan == 16 && from_x == to_x) {
                    int i = 0;
                    for (i = 0; i < 90; i++)
                        if (board[i] == 16)
                            break;
                    if (i % 9 == to_x) {
                        for (int j = from_index - 9; j > i; j -= 9)
                            if (board[j] != 0){ // gặp vật cản
                                return false;
                            }
                        return true;
                    }
                }
                // Không thể rời khỏi cung điện
                if(to_y < 7 || to_x <3 || to_x > 5){
                    Toast.makeText(this, "Không thể rời khỏi cung điện", Toast.LENGTH_SHORT).show();
                    return false;
                }
                // Đi thẳng
                if(to_index != from_index+1 && to_index != from_index-1 && to_index != from_index+9 && to_index != from_index-9)
                    return false;
                // Tướng không thể nhìn thẳng
                int i;
                for(i = 0; i < 90; i++)  // Tìm vị trí tướng địch
                    if(board[i] == 16)
                        break;
                if(i%9 == to_x) {       // Vị trí của tướng địch thẳng hàng dọc vị trí tướng mình đi tới
                    //Toast.makeText(this, "Check", Toast.LENGTH_SHORT).show();
                    for(int j = to_index-9; j > i; j-=9)  // Tìm vật cản
                        if(board[j] != 0){
                            return true;
                        }
                    Toast.makeText(this, "Không đi được vì đối mặt tướng", Toast.LENGTH_SHORT).show();
                    return false;
                }
                break;
            case 16:  // tướng đen
                if(chessMan == 32 && from_x == to_x) {
                    i = 0;
                    for (i = 0; i < 90; i++)
                        if (board[i] == 32)
                            break;
                    if (i % 9 == to_x) {
                        for (int j = from_index - 9; j > i; j -= 9)
                            if (board[j] != 0){ // gặp vật cản
                                return false;
                            }
                        return true;
                    }
                }
                if(to_y > 2 || to_x <3 || to_x > 5){
                    Toast.makeText(this, "Không thể rời khỏi cung điện", Toast.LENGTH_SHORT).show();
                    return false;
                }
                // Đi thẳng
                if(to_index != from_index+1 && to_index != from_index-1 && to_index != from_index+9 && to_index != from_index-9)
                    return false;
                i = 0;
                for(i = 0; i < 90; i++)  // Tìm vị trí tướng địch
                    if(board[i] == 32)
                        break;
                if(i%9 == to_x) {       // Vị trí của tướng địch thẳng hàng dọc vị trí tướng mình đi tới
                    for(int j = to_index+9; j < i; j+=9){
                        // Tìm vật cản
                        Log.d("AAA","to_index " + to_index);
                        Log.d("AAA","Quan " + board[j]+ chess_name(board[j]));
                        if(board[j] != 0){ // Có vật cản nên cho đi
                            return true;
                        }
                    }

                    Toast.makeText(this, "Không đi được vì đối mặt tướng", Toast.LENGTH_SHORT).show();
                    return false;
                }
                break;
            case 25: case 26: case 9: case 10: // pháo
                if(from_x != to_x && from_y != to_y) // Chỉ có thể di chuyển theo chiều ngang hoặc chiều dọc
                    return false;
                // Kiểm tra vật cản
                if(from_y == to_y) {   // Đi theo chiều dọc
                    int count = 0;  // Đếm vật cản
                    if(from_index < to_index) {
                        for(i = from_index+1; i < to_index; i++)
                            if(board[i] != 0)
                                count++;
                    } else {
                        for(i = from_index-1; i > to_index; i--)
                            if(board[i] != 0)
                                count++;
                    }
                    if(count > 1)   // Nhiều hơn 1 vật cản thì không cho đi
                        return false;
                    if(count == 0 && board[to_index] != 0) // Không có 1 vật cản nên khôn ăn được
                        return false;
                    if(count == 1 && board[to_index] == 0 ) // Có 1 vật cản nhưng không có tướng nên không đi được
                        return false;
                } else {                // Đi theo chiều ngang
                    int count = 0;
                    if(from_index < to_index) {
                        for(i = from_index+9; i < to_index; i+=9)
                            if(board[i] != 0)
                                count++;
                    } else {
                        for(i = from_index-9; i > to_index; i-=9)
                            if(board[i] != 0)
                                count++;
                    }
                    if(count > 1)   // Nhiều hơn 1 vật cản thì không cho đi
                        return false;
                    if(count == 0 && board[to_index] != 0) // Không có 1 vật cản nên khôn ăn được
                        return false;
                    if(count == 1 && board[to_index] == 0 ) // Có 1 vật cản nhưng không có tướng nên không đi được
                        return false;
                }
                break;
            case 27: case 28: case 29: case 30: case 31: // tốt đỏ
                // Không thể di chuyển qua trái hoặc phải nếu chưa qua sông
                if( from_index > 44 && from_y == to_y && (to_x == from_x-1 || to_x == from_x+1))
                    return false;
                // Không thể đi lùi
                if(!(from_y == to_y && from_x == to_x-1)&&!(from_y == to_y && from_x == to_x+1)&&!(from_x==to_x && to_y==from_y-1))
                    return false;
                break;
            case 11: case 12: case 13: case 14: case 15: // tốt đen
                // Không thể di chuyển qua trái hoặc phải nếu chưa qua sông
                if( from_index < 45 && from_y == to_y && (to_x == from_x-1 || to_x == from_x+1))
                    return false;
                // Không thể đi lùi
                if(!(from_y == to_y && from_x == to_x-1)&&!(from_y == to_y && from_x == to_x+1)&&!(from_x==to_x && to_y==from_y+1))
                    return false;
                break;
            default:
                break;
        }
        return true;
    }

    void game_over(int[] board) {
        boolean win = true;
        boolean lose = true;
        for(int i = 0; i < board.length; i++) {
            if(board[i] == 16)
                win=false;
            if(board[i] == 32)
                lose=false;
        }
        if(win) {
            over = true;
            Toast.makeText(getApplicationContext(), "You win!", Toast.LENGTH_SHORT).show();
        }
        if(lose) {
            over = true;
            Toast.makeText(getApplicationContext(), "You lose!", Toast.LENGTH_SHORT).show();
        }

    }


    // Hiển thị con đang chọn trên màn hình
    void set_choose(Drawable d) {
        ImageView choose = findViewById(R.id.choose);
        choose.setBackground(d);
    }

    public void clickCheck(View view) {
        constraintLayout = (ConstraintLayout)view.getParent();
        int id = constraintLayout.indexOfChild(view);
        if(isMyTurn&&isRed){  // Lượt của quân đỏ
            if(last_click==-1){  // Chưa có con nào được chọn trước khi click
                if(board[id]<=16 && board[id] != 0){     // Chọn quân đen thì không cho
                    Toast.makeText(this, "Lượt của quân đỏ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (board[id] > 16) {     // Nếu click vào ô có vị trí là id và đó quân đỏ thì gán last_click là id
                    ImageView target = (ImageView) constraintLayout.getChildAt(id);
                    set_choose(target.getBackground());
                    last_click = id;
                }
            }else{
                if (board[id] > 16) {     // Click vào ô id là quân đỏ thì gán last_click là quân vừa mới chọn
                    ImageView target = (ImageView) constraintLayout.getChildAt(id);
                    set_choose(target.getBackground());
                    last_click = id;
                }else if (canMove(last_click, id)) {  // Click vào ô không phải là quân đỏ thì kiểm tra có thể đi được hay không
                    update_action(last_click);

                    /**
                    * push len server
                    * */
                    if(board[id]==16){
                        mPlayer.pushMoveToServer(last_click,id);
                        mPlayer.pushActionWin(Player.KEY_RED_WIN);
                    }else{
                        mPlayer.pushMoveToServer(last_click,id);
                    }

                }
            }
        }

        if(isMyTurn&& !isRed){          // Lượt của quân đen
            if(last_click==-1){  // Chưa có con nào được chọn trước khi click
                if(board[id]>16){     // Chọn quân đỏ thì không cho
                    Toast.makeText(this, "Lượt của quân đen", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (board[id]<=16 && board[id] !=0) {     // Nếu click vào ô có vị trí là id và đó quân đen thì gán last_click là id
                    ImageView target = (ImageView) constraintLayout.getChildAt(id);
                    set_choose(target.getBackground());
                    last_click = id;
                }
            }else{
                if (board[id] <= 16 && board[id] != 0) {     // Click vào ô id là quân đen thì gán last_click là quân vừa mới chọn
                    ImageView target = (ImageView) constraintLayout.getChildAt(id);
                    set_choose(target.getBackground());
                    last_click = id;
                }else if (canMove(last_click, id)) {  // Click vào ô không phải là quân đen thì kiểm tra có thể đi được hay không
                    update_action(last_click);
                    if(board[id]==32){
                        mPlayer.pushMoveToServer(last_click,id);
                        mPlayer.pushActionWin(Player.KEY_BLACK_WIN);
                    }else {
                        mPlayer.pushMoveToServer(last_click,id);
                    }
                }
            }
        }
    }



    @Override
    public void onGamePlay(int turn) {
        if(turn==1){
            isMyTurn = true;
            isRed = true;
        }else{
            isMyTurn = false;
            isRed = false;
        }
        this.runOnUiThread(() -> Toast.makeText(MainActivity.this, "turn "+turn, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onGameWinOrLose(boolean isRed) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(MainActivity.isRed==isRed){
                    Toast.makeText(MainActivity.this, "You Win", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "You Lose", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onMove(int indexFrom, int indexTo) {
        handleMove(indexFrom,indexTo);
    }

    private void handleMove(int indexFrom, int indexTo ){
        this.runOnUiThread(() -> {
            ImageView from = (ImageView) constraintLayout.getChildAt(indexFrom);
            Drawable from_img = from.getBackground();
            constraintLayout.getChildAt(indexFrom).setBackground(null);  // Đặt vị trí vừa di chuyển là không có quân nào
            constraintLayout.getChildAt(indexTo).setBackground(from_img);
            board[indexTo] = board[indexFrom];      // gán quân cờ bị di chuyển từ vị trí cũ sang vị trí mới
            board[indexFrom] = 0;              // gán vị trí cũ là không có quân cờ nào
            set_choose(null);                   // hiển thị con cờ đang được chon
            last_click = -1;                    // gán giá trị cho lần click cuối cùng là không có quân nào
            isMyTurn = !isMyTurn;
            //gameOver(board);
            if (over) return;
        });

    }


}
