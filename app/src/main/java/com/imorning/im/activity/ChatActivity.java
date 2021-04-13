package com.imorning.im.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.imorning.im.BaseActivity;
import com.imorning.im.R;
import com.imorning.im.action.UserAction;
import com.imorning.im.adapter.ChatMessageAdapter;
import com.imorning.im.bean.ApplicationData;
import com.imorning.im.bean.ChatEntity;
import com.imorning.im.databse.ImDB;
import com.imorning.im.view.TitleBarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends BaseActivity {
    private TitleBarView mTitleBarView;
    private int friendId;
    private String friendName;
    private ListView chatMeessageListView;
    private ChatMessageAdapter chatMessageAdapter;
    private Button sendButton;
    private ImageButton emotionButton;
    private EditText inputEdit;
    private List<ChatEntity> chatList;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        friendName = intent.getStringExtra("friendName");
        friendId = intent.getIntExtra("friendId", 0);
        initViews();
        initEvents();
    }

    @Override
    protected void initViews() {
        mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
        mTitleBarView.setTitleText("与" + friendName + "对话");
        chatMeessageListView = (ListView) findViewById(R.id.chat_Listview);
        sendButton = (Button) findViewById(R.id.chat_btn_send);
        emotionButton = (ImageButton) findViewById(R.id.chat_btn_emote);
        inputEdit = (EditText) findViewById(R.id.chat_edit_input);

    }

    @SuppressLint("HandlerLeak")
	@Override
    protected void initEvents() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    chatMessageAdapter.notifyDataSetChanged();
                    chatMeessageListView.setSelection(chatList.size());
                }
            }
        };
        ApplicationData.getInstance().setChatHandler(handler);
        chatList = ApplicationData.getInstance().getChatMessagesMap()
                .get(friendId);
        if (chatList == null) {
            chatList = ImDB.getInstance(ChatActivity.this).getChatMessage(friendId);
            ApplicationData.getInstance().getChatMessagesMap().put(friendId, chatList);
        }
        chatMessageAdapter = new ChatMessageAdapter(ChatActivity.this, chatList);
        chatMeessageListView.setAdapter(chatMessageAdapter);
        sendButton.setOnClickListener(v -> {
            String content = inputEdit.getText().toString();
            inputEdit.setText("");
            ChatEntity chatMessage = new ChatEntity();
            chatMessage.setContent(content);
            chatMessage.setSenderId(ApplicationData.getInstance().getUserInfo().getId());
            chatMessage.setReceiverId(friendId);
            chatMessage.setMessageType(ChatEntity.SEND);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss", Locale.getDefault());
            String sendTime = sdf.format(date);
            chatMessage.setSendTime(sendTime);
            chatList.add(chatMessage);
            chatMessageAdapter.notifyDataSetChanged();
            chatMeessageListView.setSelection(chatList.size());
            UserAction.sendMessage(chatMessage);
            ImDB.getInstance(ChatActivity.this).saveChatMessage(chatMessage);
        });
    }

}
