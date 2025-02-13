package org.processmining.plugins.interoperability;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;

import org.processmining.framework.util.ui.widgets.ProMComboBox;
import org.processmining.framework.util.ui.widgets.ProMHeaderPanel;
import org.processmining.framework.util.ui.widgets.ProMScrollablePanel;
import org.processmining.framework.util.ui.widgets.ProMTextField;
import org.processmining.framework.util.ui.widgets.WidgetColors;

import com.fluxicon.slickerbox.components.RoundedPanel;
import com.fluxicon.slickerbox.factory.SlickerFactory;
import com.fluxicon.slickerbox.ui.SlickerScrollBarUI;


public class SolverDialog extends ProMHeaderPanel {


	private static final long serialVersionUID = 1L;

	private final JPanel properties;

	private boolean first = true;

	/**
	 * @param title
	 */
	public SolverDialog(final String title) {
		super(title);
		
		JLabel label = new javax.swing.JLabel();
		 label.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
	     label.setText("To get started, you need to import an Event Log and ");
	     
	     JLabel label0 = new javax.swing.JLabel();
		 label0.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
	     label0.setText("its corresponding Process Model.");
	     
	     JLabel label1 = new javax.swing.JLabel();
		 label1.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
	     label1.setText(" ");
	     
	     JLabel label2 = new javax.swing.JLabel();
		 label2.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
	     label2.setText("Click 'Continue' to import the files.");
	     
	     add(label);
	     add(label0);
	     add(label1);
	     add(label2);
	     
		properties = new ProMScrollablePanel();
		properties.setOpaque(false);
		properties.setLayout(new BoxLayout(properties, BoxLayout.Y_AXIS));
		final JScrollPane scrollPane = new JScrollPane(properties);
		scrollPane.setOpaque(true);
		scrollPane.setBackground(WidgetColors.PROPERTIES_BACKGROUND);
		scrollPane.getViewport().setOpaque(true);
		scrollPane.getViewport().setBackground(WidgetColors.PROPERTIES_BACKGROUND);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar vBar = scrollPane.getVerticalScrollBar();
		vBar.setUI(new SlickerScrollBarUI(vBar, new Color(0, 0, 0, 0), new Color(160, 160, 160),
				WidgetColors.COLOR_NON_FOCUS, 4, 12));
		vBar.setOpaque(true);
		vBar.setBackground(WidgetColors.PROPERTIES_BACKGROUND);
		vBar = scrollPane.getHorizontalScrollBar();
		vBar.setUI(new SlickerScrollBarUI(vBar, new Color(0, 0, 0, 0), new Color(160, 160, 160),
				WidgetColors.COLOR_NON_FOCUS, 4, 12));
		vBar.setOpaque(true);
		vBar.setBackground(WidgetColors.PROPERTIES_BACKGROUND);
		//add(scrollPane);
		
		 
	}

	public JCheckBox addCheckBox(final String name, final int indentLevel, final int nameWidth) {
		return addCheckBox(name, false, indentLevel, nameWidth);
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public JCheckBox addCheckBox(final String name, final boolean value, final int indentLevel, final int nameWidth) {
		final JCheckBox checkBox = SlickerFactory.instance().createCheckBox(null, value);
		return addProperty(name, checkBox, indentLevel, nameWidth);
	}

	/**
	 * @param name
	 * @param values
	 * @return
	 */
	public JComboBox addComboBox(final String name, final Iterable<Object> values, final int indentLevel, final int nameWidth) {
		return addProperty(name, new ProMComboBox(values), indentLevel, nameWidth);
	}

	/**
	 * @param name
	 * @param values
	 * @return
	 */
	public JComboBox addComboBox(final String name, final Object[] values, final int indentLevel, final int nameWidth) {
		return addProperty(name, new ProMComboBox(values), indentLevel, nameWidth);
	}

	/**
	 * @param <T>
	 * @param name
	 * @param component
	 * @return
	 */
	public <T extends JComponent> T addProperty(final String name, final T component, final int indentLevel, final int nameWidth) {
		if (!first) {
			properties.add(Box.createVerticalStrut(3));
		} else {
			first = false;
		}
		properties.add(packInfo(name, component, indentLevel, nameWidth));
		return component;
	}
	
	/**
	 * @param <T>
	 * @param name
	 * @param component
	 * @return
	 */
	public <T extends Component> T addToProperties(final T component) {
		properties.add(component);
		return component;
	}

	/**
	 * @param name
	 * @return
	 */
	public ProMTextField addTextField(final String name, final int indentLevel, final int nameWidth) {
		return addTextField(name, "", indentLevel, nameWidth);
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public ProMTextField addTextField(final String name, final String value, final int indentLevel, final int nameWidth) {
		final ProMTextField component = new ProMTextField();
		component.setText(value);
		return addProperty(name, component, indentLevel, nameWidth);
	}

	private Component findComponent(final Component component) {
		if (component instanceof AbstractButton)
			return component;
		if (component instanceof JTextComponent)
			return component;
		if (component instanceof Container) {
			for (final Component child : ((Container) component).getComponents()) {
				final Component result = findComponent(child);
				if (result != null)
					return result;
			}
		}
		return null;
	}

	private void installHighlighter(final Component component, final RoundedPanel target) {
		component.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(final MouseEvent arg0) { /* ignore */
			}

			@Override
			public void mouseEntered(final MouseEvent arg0) {
				target.setBackground(new Color(60, 60, 60, 240));
				target.repaint();
			}

			@Override
			public void mouseExited(final MouseEvent arg0) {
				target.setBackground(new Color(60, 60, 60, 160));
				target.repaint();
			}

			@Override
			public void mousePressed(final MouseEvent arg0) { /* ignore */
			}

			@Override
			public void mouseReleased(final MouseEvent arg0) { /* ignore */
			}
		});
		if (component instanceof Container) {
			for (final Component child : ((Container) component).getComponents()) {
				installHighlighter(child, target);
			}
		}
	}

	protected JPanel packInfo(final String name, final JComponent component, final int indentLevel, final int nameWidth) {
		final RoundedPanel packed = new RoundedPanel(10, 0, 0);
		packed.setBackground(new Color(60, 60, 60, 160));
		final RoundedPanel target = packed;
		final Component actualComponent = findComponent(component);
		packed.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(final MouseEvent arg0) {
				if (actualComponent != null) {
					if (actualComponent instanceof AbstractButton) {
						final AbstractButton button = (AbstractButton) actualComponent;
						button.doClick();
					}
					if (actualComponent instanceof JTextComponent) {
						final JTextComponent text = (JTextComponent) actualComponent;
						if (text.isEnabled() && text.isEditable()) {
							text.selectAll();
						}
						text.grabFocus();
					}
				}
			}

			@Override
			public void mouseEntered(final MouseEvent arg0) {
				target.setBackground(new Color(60, 60, 60, 240));
				target.repaint();
			}

			@Override
			public void mouseExited(final MouseEvent arg0) {
				target.setBackground(new Color(60, 60, 60, 160));
				target.repaint();
			}

			@Override
			public void mousePressed(final MouseEvent arg0) { /* ignore */
			}

			@Override
			public void mouseReleased(final MouseEvent arg0) { /* ignore */
			}
		});
		installHighlighter(component, target);
		packed.setLayout(new BoxLayout(packed, BoxLayout.X_AXIS));
		final JLabel nameLabel = new JLabel(name);
		nameLabel.setOpaque(false);
		nameLabel.setForeground(WidgetColors.TEXT_COLOR);
		nameLabel.setFont(nameLabel.getFont().deriveFont(12f));
		nameLabel.setMinimumSize(new Dimension(nameWidth, 20));
		nameLabel.setMaximumSize(new Dimension(nameWidth, 1000));
		nameLabel.setPreferredSize(new Dimension(nameWidth, 30));

		packed.add(Box.createHorizontalStrut(5));
		packed.add(nameLabel);
		packed.add(Box.createHorizontalGlue());
		packed.add(component);
		packed.add(Box.createHorizontalStrut(5));
		packed.revalidate();

		final JPanel packedContainer = new JPanel();
		packedContainer.setLayout(new BoxLayout(packedContainer, BoxLayout.X_AXIS));
		packedContainer.setOpaque(false);
		packedContainer.add(Box.createHorizontalStrut(indentLevel*10));
		packedContainer.add(packed);
		packedContainer.revalidate();
		
		return packedContainer;
	}

}
