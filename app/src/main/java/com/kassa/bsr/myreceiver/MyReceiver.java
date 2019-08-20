package com.kassa.bsr.myreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

public class MyReceiver {

    private static List<InnerWrapper> innerWrappers=new ArrayList<>();

    public static void  add(Context context, String filter, final IAction<String> action){

        for (InnerWrapper innerWrapper : innerWrappers) {
            if(innerWrapper.filter.equals(filter))
                return;
        }


        final InnerWrapper innerWrapper=new InnerWrapper();
        innerWrapper.context =context;
        innerWrapper.filter=filter;
        innerWrapper.filter_msg=filter+"_msg";
        innerWrapper.receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra(innerWrapper.filter_msg);
                if(action!=null){
                    action.action(message);
                }

            }
        };
        IntentFilter filterCommonMessage = new IntentFilter();
        filterCommonMessage.addAction(filter);
        context.registerReceiver(innerWrapper.receiver,filterCommonMessage);
        innerWrappers.add(innerWrapper);
    }

    public static void deatach(Context activity){
        List<InnerWrapper> innerWrappersDelete=new ArrayList<>();
        for (InnerWrapper wrapper : innerWrappers) {
            if(wrapper.context ==activity){
                innerWrappersDelete.add(wrapper);
            }
        }
        for (InnerWrapper innerWrapper : innerWrappersDelete) {
            activity.unregisterReceiver(innerWrapper.receiver);
        }
        MyReceiver.innerWrappers.removeAll(innerWrappersDelete);
    }



    public static void sendMessage(Context context,String filter,String msg){
        Intent intent=new Intent(filter);
        intent.putExtra(filter+"_msg",msg);
        context.sendBroadcast(intent);
    }

    public static class InnerWrapper{
        public String filter;
        public String filter_msg;
        public Context context;
        public BroadcastReceiver receiver;
    }
}
