package de.fhtrier.gdig.engine.helpers;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.org.apache.xpath.internal.compiler.Keywords;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.engine.helpers.Configuration.CommandlineParameter;
import de.fhtrier.gdig.engine.helpers.Configuration.DefaultTrue;

/**
 * This abstract class allows you to easily configurate your program. To use its
 * functionality extend from this class. Every {@link Keywords int},
 * {@link Keywords boolean}, {@link String}, {@link Keywords float},
 * {@link Enum} decleared in your class kan be used.
 * 
 * Call save to save the configuration to a Stream or load to load your
 * configuration to a stream. you can also pars commandline Arguments to set
 * your parameters. To do this you must use the {@link CommandlineParameter}
 * Annotation. you can now use the argument with --ArgumentName value, which is
 * specified in the Annotation.
 * 
 * Boolean attributes automaticly get set to true when the argument is submitted
 * without a parameter. To set it to false when the parameter is called, use the
 * {@link DefaultTrue} annotation.
 * 
 * 
 * 
 * 
 * @author Loki
 * 
 */
public abstract class Configuration {

	/**
	 * den Namen welcher als Kommandozeilen argument übergeben wird.
	 * 
	 * @return Den namen
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	protected @interface CommandlineParameter {

		String value();
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	protected @interface ShowAsSlider {

		int maxValue();

		int minValue();
	}

	/**
	 * Boolean werte welche standardmäßig true sind können mit dieser Annotation
	 * versehen werden. Hierdurch wird wenn das entsprechende argument gepharst
	 * wird, anstelle true der wert auf false gesetzt.
	 * 
	 * @author Loki
	 * 
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	protected @interface DefaultTrue {
	}

	/**
	 * This Annotation allows you to suppress the apearing of this Field in the
	 * Editor window.
	 * 
	 * @author Loki
	 * 
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	protected @interface DontShowInEditor {
	}

	private Map<String, Field> commandMap;

	public Configuration() {

		commandMap = new HashMap<String, Field>();
		Field[] declaredFields = getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			CommandlineParameter nameAnnotation = field
					.getAnnotation(CommandlineParameter.class);
			if (nameAnnotation != null) {
				if (!checkFeasabel(field.getType()))
					throw new IllegalArgumentException(
							"Field is not of Supported as Command");
				commandMap.put(nameAnnotation.value(), field);
			}
		}
	}

	/**
	 * checks if this type of variable is supported
	 * 
	 * @param object
	 * @return
	 */
	private boolean checkFeasabel(Class<?> object) {
		if (object == String.class)
			return true;
		if (object == int.class)
			return true;
		if (object == float.class)
			return true;
		if (object == InetSocketAddress.class)
			return true;
		if (object == File.class)
			return true;
		if (object == boolean.class)
			return true;
		return false;
	}

	@SuppressWarnings("rawtypes")
	public void save(OutputStream out) {
		try {
			Properties p = new Properties();
			for (Field field : getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if ((field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT)
					continue;

				String value = null;

				Class<?> fieldType = field.getType();
				if (fieldType == String.class) {
					value = (String) field.get(this);
				} else if (fieldType == int.class) {
					value = field.get(this).toString();
				} else if (fieldType == float.class) {
					value = field.get(this).toString();
				} else if (fieldType == boolean.class) {
					value = field.get(this).toString();
				} else if (fieldType == InetSocketAddress.class) {
					value = field.get(this).toString();
				} else if (fieldType == File.class) {
					value = field.get(this).toString();
				} else if (fieldType.isEnum()) {
					Enum string = (Enum) field.get(this);
					value = string.name();
				}

				if (value != null) {
					p.setProperty(field.getName(), value);
				}
				field.setAccessible(false);
			}
			p.store(out, "");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void load(InputStream inStream) {
		try {
			Properties p = new Properties();
			p.load(inStream);
			for (Field field : getClass().getDeclaredFields()) {
				field.setAccessible(true);
				String value = p.getProperty(field.getName());
				if ((field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT)
					continue;
				if (value == null)
					continue;

				Class<?> filedType = field.getType();
				if (filedType == String.class) {
					field.set(this, value);
				} else if (filedType == int.class) {
					field.set(this, Integer.parseInt(value));
				} else if (filedType == float.class) {
					field.set(this, Float.parseFloat(value));
				} else if (filedType == boolean.class) {
					field.set(this, Boolean.parseBoolean(value));
				} else if (filedType == InetSocketAddress.class) {
					if (value.contains(":")) {
						String[] split = value.split(":");
						if (split.length != 2)
							throw new IllegalArgumentException(
									"IP Adresse Kann nicht gephrast werden, es werden nur ipv4 Unterstützt.");
						InetSocketAddress inetSocketAddress = new InetSocketAddress(
								split[0], Integer.parseInt(split[1]));
						field.set(this, inetSocketAddress);
					} else {
						InetSocketAddress inetSocketAddress = new InetSocketAddress(
								value, 0);
						field.set(this, inetSocketAddress);
					}

				} else if (filedType == File.class) {
					field.set(this, new File(value));
				} else if (filedType.isEnum()) {
					Class<? extends Enum> type = (Class<? extends Enum>) field
							.getType();
					Enum selectedEnum = Enum.valueOf(type, value);
					field.set(Configuration.this, selectedEnum);
				}
				field.setAccessible(false);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void parseCommandLine(String[] args) {
		try {
			for (int i = 0; i < args.length; ++i) {
				if (!args[i].startsWith("--"))
					continue;

				Field field = this.commandMap.get(args[i].substring(2));
				if (field != null) {
					++i;
					Class<?> filedType = field.getType();
					if (filedType == String.class) {
						field.set(this, args[i]);
					} else if (filedType == int.class) {
						field.set(this, Integer.parseInt(args[i]));
					} else if (filedType == float.class) {
						field.set(this, Float.parseFloat(args[i]));
					} else if (filedType == boolean.class) {
						--i;
						DefaultTrue shuldBeFalse = field
								.getAnnotation(DefaultTrue.class);
						field.set(this, shuldBeFalse == null);
					} else if (filedType == InetSocketAddress.class) {
						if (args[i].contains(":")) {
							String[] split = args[i].split(":");
							if (split.length != 2)
								throw new IllegalArgumentException(
										"ip address not parsable. only ipv4 supported");
							InetSocketAddress inetSocketAddress = new InetSocketAddress(
									split[0], Integer.parseInt(split[1]));
							field.set(this, inetSocketAddress);
						} else {
							InetSocketAddress inetSocketAddress = new InetSocketAddress(
									args[i], 0);
							field.set(this, inetSocketAddress);
						}
					} else if (filedType == File.class) {
						field.set(this, new File(args[i]));
					} else if (filedType.isEnum()) {
						Class<? extends Enum> type = (Class<? extends Enum>) field
								.getType();
						Enum selectedEnum = Enum.valueOf(type, args[i]);
						field.set(Configuration.this, selectedEnum);
					}
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void showEditor(String strTitle) {

		if (Constants.Debug.showDialogs) {
			showEditor(strTitle, new JPanel[] { getEdittingPanel() });
		}
	}

	public static void showEditor(String strTitle, JPanel[] panels) {

		if (Constants.Debug.showDialogs) {
			showEditor(strTitle, panels, null);
		}
	}

	public static void showEditor(String strTitle, JPanel[] panels,
			Point location) {

		if (Constants.Debug.showDialogs) {
			JFrame f = new JFrame(strTitle);
			if (location != null)
				f.setLocation(location);
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JPanel p = new JPanel();

			BoxLayout boxLayout = new BoxLayout(p, BoxLayout.X_AXIS);
			p.setLayout(boxLayout);

			for (JPanel jPanel : panels) {
				p.add(jPanel);
			}
			f.add(new JScrollPane(p));
			f.pack();
			f.setVisible(true);
		}
	}

	public JPanel getEdittingPanel() {

		try {
			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createTitledBorder(getClass()
					.getSimpleName()));
			BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
			panel.setLayout(boxLayout);

			for (final Field field : getClass().getDeclaredFields()) {
				field.setAccessible(true);
				Object value = field.get(this);
				JLabel jLabel = new JLabel(field.getName());
				panel.add(jLabel);

				Class<?> filedType = field.getType();
				DontShowInEditor dontShowInEditor = field
						.getAnnotation(DontShowInEditor.class);
				if (dontShowInEditor != null) {
					panel.remove(jLabel);

				} else if (filedType == String.class) {

					final JTextField jTextField = new JTextField((String) value);
					jTextField.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								field.setAccessible(true);
								field.set(Configuration.this,
										jTextField.getText());
								field.setAccessible(false);
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					panel.add(jTextField);
				} else if (field.getType().isEnum()) {
					Object[] enumConstants = field.getType().getEnumConstants();
					final JComboBox combo = new JComboBox(enumConstants);
					panel.add(combo);
					// Todo
					combo.addActionListener(new ActionListener() {

						@SuppressWarnings({ "rawtypes", "unchecked" })
						@Override
						public void actionPerformed(ActionEvent e) {
							Class<? extends Enum> type = (Class<? extends Enum>) field
									.getType();
							Enum selectedEnum = Enum.valueOf(type, combo
									.getSelectedItem().toString());
							try {
								field.setAccessible(true);
								field.set(Configuration.this, selectedEnum);
								field.setAccessible(false);
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					});
				} else if (filedType == int.class) {
					ShowAsSlider showSlider = field
							.getAnnotation(ShowAsSlider.class);
					if (showSlider != null) {
						int maxValue = showSlider.maxValue();
						int minValue = showSlider.minValue();
						final JSlider jSlider = new JSlider(minValue, maxValue,
								((Integer) value).intValue());
						panel.add(jSlider);
						final JLabel anzeige = new JLabel(Integer.valueOf(
								jSlider.getValue()).toString());
						panel.add(anzeige);
						jSlider.addChangeListener(new ChangeListener() {

							@Override
							public void stateChanged(ChangeEvent arg0) {
								try {
									field.setAccessible(true);
									anzeige.setText(Integer.valueOf(
											jSlider.getValue()).toString());
									field.set(Configuration.this,
											jSlider.getValue());
									field.setAccessible(false);
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});

					} else {
						SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(
								((Integer) value).intValue(),
								Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
						final JSpinner jSpinner = new JSpinner(
								spinnerNumberModel);
						panel.add(jSpinner);
						jSpinner.setPreferredSize(new Dimension(60, jSpinner
								.getPreferredSize().height));
						jSpinner.addChangeListener(new ChangeListener() {

							@Override
							public void stateChanged(ChangeEvent e) {
								try {
									field.setAccessible(true);
									field.set(Configuration.this,
											((Integer) jSpinner.getValue())
													.intValue());
									field.setAccessible(false);
								} catch (IllegalArgumentException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IllegalAccessException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
						});
					}
				} else if (filedType == float.class) {
					ShowAsSlider showSlider = field
							.getAnnotation(ShowAsSlider.class);
					if (showSlider != null) {
						int maxValue = showSlider.maxValue();
						int minValue = showSlider.minValue();
						final JSlider jSlider = new JSlider(minValue, maxValue,
								((Float) value).intValue());
						panel.add(jSlider);
						final JLabel anzeige = new JLabel(Integer.valueOf(
								jSlider.getValue()).toString());
						panel.add(anzeige);
						jSlider.addChangeListener(new ChangeListener() {

							@Override
							public void stateChanged(ChangeEvent arg0) {
								try {
									field.setAccessible(true);
									field.set(Configuration.this, Integer
											.valueOf(jSlider.getValue())
											.floatValue());
									anzeige.setText(Integer.valueOf(
											jSlider.getValue()).toString());
									field.setAccessible(false);
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});

					} else {
						SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(
								((Float) value).floatValue(),
								-Double.MAX_VALUE, Double.MAX_VALUE, 1);
						final JSpinner jSpinner = new JSpinner(
								spinnerNumberModel);
						panel.add(jSpinner);
						final JSpinner.NumberEditor editor = new JSpinner.NumberEditor(
								jSpinner, "0.##########");
						jSpinner.setEditor(editor);
						jSpinner.setPreferredSize(new Dimension(60, jSpinner
								.getPreferredSize().height));
						jSpinner.addChangeListener(new ChangeListener() {

							@Override
							public void stateChanged(ChangeEvent e) {
								try {
									field.setAccessible(true);
									field.set(Configuration.this,
											((Double) jSpinner.getValue())
													.floatValue());
									field.setAccessible(false);
								} catch (IllegalArgumentException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IllegalAccessException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
						});
					}
				} else if (filedType == boolean.class) {
					final JCheckBox jCheckBox = new JCheckBox();
					Boolean b = (Boolean) field.get(this);
					jCheckBox.setSelected(b);
					panel.add(jCheckBox);
					jCheckBox.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								field.setAccessible(true);
								field.set(Configuration.this,
										(jCheckBox.isSelected()));
								field.setAccessible(false);
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
				} else {
					panel.remove(jLabel);
				}
				// if (filedType == InetSocketAddress.class)
				// {
				// value = field.get(this).toString();
				// }
				// if (filedType == File.class)
				// {
				// value = field.get(this).toString();
				// }
				field.setAccessible(false);
			}
			return panel;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
