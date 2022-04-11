package com.example.chatting_app_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GroupClickListener implements View.OnClickListener {

    String GroupCode;

    public GroupClickListener() {
    }

    public GroupClickListener(String groupCode) {
        GroupCode = groupCode;
    }

    @Override
    public void onClick(View view) {

        Intent IntentForGroup=new Intent(view.getContext(),GroupPageActivity.class);
        Bundle bundle =new Bundle();
        IntentForGroup.putExtra("GroupCode",GroupCode);
        view.getContext().startActivity(IntentForGroup);

    }
}
