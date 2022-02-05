//package com.example.wordsearchgame;
//
//public class TestClass {
//
//    @Override
//
//    public boolean dispatchTouchEvent(MotionEvent ev) {undefined
//
//        LayoutManager manager = getLayoutManager();
//
//        //获取第一个和最后一个显示的Item对应的相对Position
//
//        if (manager instanceof LinearLayoutManager) {undefined
//
//                mFirstVisiblePosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
//
//            mLastVisiblePosition = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
//
//        }
//
//        switch (ev.getAction()) {undefined
//
//            case MotionEvent.ACTION_DOWN:
//
//                //获取按下时的位置,x,y
//
//                int startX = (int) ev.getX();
//
//                int startY = (int) ev.getY();
//
//                int preX = startX;
//
//                mPreY = startY;
//
//                mPreFirstVisiblePosition = mFirstVisiblePosition;
//
//                mPrePosition = mStartPosition = pointToPosition(startX, startY);
//
//                if (mStartPosition > -1) {undefined
//
//                    //获取当前Item的View
//
//                    View child = getChildAt(mStartPosition);
//
//                    if (null != child) {undefined
//
//                        //获取响应域，一般响应域里面就是一个CheckBox
//
//                        View tmpCheckBoxContainer = child.findViewWithTag("checkbox_layout");
//
//                        if (null != tmpCheckBoxContainer && tmpCheckBoxContainer.getVisibility() == VISIBLE) {undefined
//
//                                mCheckBoxWidth = tmpCheckBoxContainer.getWidth();
//
////获取响应域的范围，一定要用这种获取绝对位置的方式，不然会受到padding或者是margin的影响
//
//                            int[] location = new int[2];
//
//                            tmpCheckBoxContainer.getLocationOnScreen(location);
//
//                            mCheckBoxX = location[0];
//
////判断按下的位置是否是在响应域内
//
//                            if (startX >= mCheckBoxX && startX <= (mCheckBoxX + mCheckBoxWidth)) {undefined
//
//                                Log.d(LOG_TAG, "dispatchTouchEvent() DOWN mStartPosition: " + mStartPosition);
//
////设置截取事件的标志位
//
//                                mIsNeedScrollCheck = true;
//
////设置为第一次滑动，这是用作判断折返的
//
//                                mIsFirstMove = true;
//
//                                setStartCheckBoxState();
//
////截获Checkbox的点击事件，防止两次选中
//
//                                return true;
//
//                            } else {undefined
//
//                                    mIsNeedScrollCheck = false;
//
//                            }
//
//                        } else {undefined
//
//                                mIsNeedScrollCheck = false;
//
//                            Log.e(LOG_TAG, "dispatchTouchEvent() ", new Throwable("Cannot get CheckBoxContainer!"));
//
//                        }
//
//                    } else {undefined
//
//                        Log.e(LOG_TAG, "dispatchTouchEvent() ", new Throwable("Cannot get item view!"));
//
//                    }
//
//                }
//
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//
////获取当前位置
//
//                int currentX = (int) ev.getX();
//
//                int currentY = (int) ev.getY();
//
////获取当前的item
//
//                int currentPosition = pointToPosition(currentX, currentY);
//
////判断是否允许滑动选中
//
//                if (mIsNeedScrollCheck && -1 != mFirstVisiblePosition && -1 != mLastVisiblePosition && -1 != currentPosition) {undefined
//
////判断是否在下一个Item的像英语
//
//                    if ((currentPosition + mFirstVisiblePosition) != (mPrePosition + mPreFirstVisiblePosition) &&
//
//                            currentX >= mCheckBoxX && currentX <= (mCheckBoxX + mCheckBoxWidth)) {undefined
//
//                        Log.i(LOG_TAG, "********************************** dispatchTouchEvent() ********************************");
//
//                        Log.d(LOG_TAG, "dispatchTouchEvent() MOVE mCurrentPosition: " + currentPosition);
//
//                        Log.d(LOG_TAG, "dispatchTouchEvent() MOVE mFirstVisiblePosition: " + mFirstVisiblePosition);
//
//                        Log.d(LOG_TAG, "dispatchTouchEvent() MOVE mPrePosition: " + mPrePosition);
//
//                        Log.d(LOG_TAG, "dispatchTouchEvent() MOVE mPreFirstVisiblePosition: " + mPreFirstVisiblePosition);
//
//                        Log.i(LOG_TAG, "********************************** dispatchTouchEvent() ********************************");
//
////折返回来时要改变前一个的Checkbox的状态
//
//                        if (mIsFirstMove) {undefined
//
//                                mIsFirstMove = false;
//
//                            if (currentY >= mPreY) {undefined
//
//                                    mUpOrDown = false;
//
//                            } else {undefined
//
//                                    mUpOrDown = true;
//
//                            }
//
//                        } else {undefined
//
//                            if ((currentPosition + mFirstVisiblePosition) > (mPrePosition + mPreFirstVisiblePosition) && mUpOrDown) {undefined
//
//                                changeCheckBoxState(mPrePosition);
//
//                                mUpOrDown = false;
//
//                            } else if ((currentPosition + mFirstVisiblePosition) < (mPrePosition + mPreFirstVisiblePosition) && !mUpOrDown) {undefined
//
//                                changeCheckBoxState(mPrePosition);
//
//                                mUpOrDown = true;
//
//                            }
//
//                        }
//
//                        changeCheckBoxState(currentPosition);
//
//                    }
//
////判断是否是在最后一个item上滑动，如果是则进行自动向下滑动，如果是在第一个上下滑动，则自动向上滑动
//
////Log.d(LOG_TAG, "dispatchTouchEvent() MOVE: " + (mLastVisiblePosition - mCurrentPosition - mFirstVisiblePosition));
//
//                    if ((mLastVisiblePosition - mFirstVisiblePosition - currentPosition) < 1 && currentY > mPreY) {undefined
//
////自动向下滑
//
//                        Log.d(LOG_TAG, "dispatchTouchEvent() MOVE mCount: " + mCount);
//
//                        View child = getChildAt(currentPosition);
//
//                        if (null != child && 0 == mCount % 5) {undefined
//
//                            scrollToPosition(mLastVisiblePosition + 1);
//
//                        }
//
//                        mCount++;
//
//                    } else if (currentPosition < 2 && currentY < mPreY) {undefined
//
////自动向上滑
//
//                        View child = getChildAt(currentPosition);
//
//                        Log.d(LOG_TAG, "dispatchTouchEvent() MOVE mCount: " + mCount);
//
////mCount用于降低滑动的频率，频率太快容易滑动的看不清楚
//
//                        if (null != child && 0 == mCount % 5) {undefined
//
//                            scrollToPosition(mFirstVisiblePosition - 1);
//
//                        }
//
//                        mCount++;
//
//                    }
//
//                    mPreY = currentY;
//
//                    mPrePosition = currentPosition;
//
//                    mPreFirstVisiblePosition = mFirstVisiblePosition;
//
//                    return true;
//
//                }
//
//                break;
//
//            case MotionEvent.ACTION_UP:
//
//                if (mIsNeedScrollCheck) {undefined
//
//                        mCount = 0;
//
//                    return false;
//
//                }
//
//                break;
//
//        }
//
//        return super.dispatchTouchEvent(ev);
//
//    }
//
//    其他的代码片段
//
////改变开始的CheckBox状态
//
//    private void setStartCheckBoxState() {undefined
//
//        View child = getChildAt(mStartPosition);
//
//        if (null != child) {undefined
//
//            ViewGroup checkBoxContainer = (ViewGroup) child.findViewWithTag("checkbox_layout");
//
//            if (null != checkBoxContainer) {undefined
//
//                CheckBox checkBox = (CheckBox) checkBoxContainer.getChildAt(0);
//
//                if (null != checkBox && checkBox.getVisibility() == VISIBLE) {undefined
//
//                    checkBox.toggle();
//
//                }
//
//            }
//
//        }
//
//    }
//
////判断当前Item的Position，相对位置
//
//    private int pointToPosition(int x, int y) {undefined
//
//        Rect frame = mTouchFrame;
//
//        if (frame == null) {undefined
//
//                mTouchFrame = new Rect();
//
//            frame = mTouchFrame;
//
//        }
//
//        final int count = getChildCount();
//
//        for (int i = count - 1; i >= 0; i--) {undefined
//
//            final View child = getChildAt(i);
//
//            if (child.getVisibility() == View.VISIBLE) {undefined
//
//                child.getHitRect(frame);
//
//                if (frame.contains(x, y)) {undefined
//
//                    return i;
//
//                }
//
//            }
//
//        }
//
//        return -1;
//
//    }
//
////改变Position的选中状态
//
//    public void changeCheckBoxState(int position) {undefined
//
//        if (position < 0 || position >= getChildCount()) {undefined
//
//            return;
//
//        }
//
//        View child = getChildAt(position);
//
//        if (null != child) {undefined
//
//            ViewGroup checkBoxLayout = (ViewGroup) child.findViewWithTag("checkbox_layout");
//
//            if (null != checkBoxLayout && checkBoxLayout.getVisibility() == VISIBLE) {undefined
//
//                CheckBox checkBox = (CheckBox) checkBoxLayout.getChildAt(0);
//
//                if (null != checkBox) {undefined
//
//                    Log.d(LOG_TAG, "changeCheckBoxState() selectCheckBox: " + position);
//
////checkBox.performClick();
//
//                    checkBox.toggle();
//
////checkBox.setClickable(false);
//
////checkBox.callOnClick();
//
//                }
//
//            }
//
//        }
//
//    }
//}
