package com.hc.android.huixin.view;

import java.util.Collections;  
import java.util.Comparator;  
import java.util.List;  
  
import android.hardware.Camera;  
import android.hardware.Camera.Size;  
import android.util.Log;  
  
public class MyCamPara {  
    private static final String tag = "yan";  
    private CameraSizeComparator sizeComparator = new CameraSizeComparator();  
    private static MyCamPara myCamPara = null;  
    private MyCamPara(){  
          
    }  
    public static MyCamPara getInstance(){  
        if(myCamPara == null){  
            myCamPara = new MyCamPara();  
            return myCamPara;  
        }  
        else{  
            return myCamPara;  
        }  
    }  
      
    public  Size getPreviewSize(List<Camera.Size> list, int th){  
        Collections.sort(list, sizeComparator);  
          
        int i = 0;  
        for(Size s:list){  
        	Log.i(tag, "�����ߴ�:w = " + s.width + "h = " + s.height);  
             
            if((s.width < th) && equalRate(s, 1.33f)){
                Log.i(tag, "��������Ԥ���ߴ�:w = " + s.width + "h = " + s.height);  
                break;  
            }  
            i++;  
        }  
  
        return list.get(i);  
    }  
    public Size getPictureSize(List<Camera.Size> list, int th){  
        Collections.sort(list, sizeComparator);  
          
        int i = 0;  
        for(Size s:list){  
        	Log.i(tag, "�����ߴ�:w = " + s.width + "h = " + s.height);  
            
            if((s.width < th) && equalRate(s, 1.33f)){  
                Log.i(tag, "��������ͼƬ�ߴ�:w = " + s.width + "h = " + s.height);  
                break;  
            }
            i++;  
        }  
        return list.get(i);  
    }  
      
    
    /**
     * ��ȡͬʱ����Ԥ�������ճߴ��Camera.Size
     * @param Frelist
     * @param Piclist
     * @param th
     * @return
     */
	public Size getPretureSizeaAndPicviewSize(List<Camera.Size> Frelist, List<Camera.Size> Piclist, int th) {
		 Collections.sort(Frelist, sizeComparator);
		Collections.sort(Piclist, sizeComparator);

		int i = 0;
		for (Size FreS : Frelist) {
			Log.i(tag, "Ԥ�������ߴ�:w = " + FreS.width + "h = " + FreS.height);

			if ((FreS.width < th) && equalRate(FreS, 1.3333f)) {
				Log.i(tag, "��ѯ������Ԥ��ͼƬ�ߴ�:w = " + FreS.width + "h = " + FreS.height);
				for (Size PicS : Piclist) {
					if ((PicS.width < th) && equalRate(PicS, 1.3333f)) {

						//�ҵ�Ԥ��������һ���ĳ���
						if (FreS.width == PicS.width && FreS.height == PicS.height) {
							return Frelist.get(i);
						}
					}
				}
			}
			i++;
		}

		return getPictureSize(Piclist, th);
	}  
    
    
    
    public boolean equalRate(Size s, float rate){  
        float r = (float)(s.width)/(float)(s.height); 
      
        if(Math.abs(r - rate) <= 0.00334)  
        {     
            return true;  
        }  
        else{  
            return false;  
        }  
    }  
      
    public  class CameraSizeComparator implements Comparator<Camera.Size>{  
        //����������  
        public int compare(Size lhs, Size rhs) {  
            // TODO Auto-generated method stub  
            if(lhs.width == rhs.width){  
            return 0;  
            }  
            else if(lhs.width > rhs.width){  
                return -1;  
            }  
            else{  
                return 1;  
            }  
        }  
          
    }  
}  