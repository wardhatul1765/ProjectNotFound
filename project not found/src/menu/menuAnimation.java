/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;


public class menuAnimation {
    
    public static void showMenu(Component component, MenuItem item, MigLayout layout, boolean show) {
        int height = component.getPreferredSize().height;
        Animator animator = new Animator(300, new TimingTargetAdapter(){
            @Override
            public void timingEvent(float fraction) {
                float f = show ? fraction : 1f - fraction;
                layout.setComponentConstraints(component, "h "+ height * f + "!");
                item.setAnimation(f);
                component.revalidate();
                item.repaint();
                item.revalidate();
            }
           
        });
        
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        animator.start();
    }
}
