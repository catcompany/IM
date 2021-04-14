package com.imorning.im.database;

/**
 * 消息数据库
 */
enum MSG_COLUMN {
    SEND_ID(1), GET_ID(2), MSG(3), TRAN_TYPE(4), TIME(5), RESULT_TYPE(6), MESSAGE_TYPE(7), SEND_NAME(8);
    int value;
    MSG_COLUMN(int column) {
        this.value = column;
    }
}
