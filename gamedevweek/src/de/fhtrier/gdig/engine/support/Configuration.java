package de.fhtrier.gdig.engine.support;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class Configuration
{

	/**
	 * den Namen welcher als Kommandozeilen argument übergeben wird.
	 * 
	 * @return Den namen
	 */
	@Retention(RetentionPolicy.RUNTIME)
	protected @interface MappingName
	{

		String value();
	}

	private Map<String, Field> commandMap;

	public Configuration()
	{
		commandMap = new HashMap<String, Field>();
		Field[] declaredFields = getClass().getDeclaredFields();
		for (Field field : declaredFields)
		{
			MappingName nameAnnotation = field.getAnnotation(MappingName.class);
			if (nameAnnotation != null)
			{
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
	private boolean checkFeasabel(Class<?> object)
	{
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

	public void save(OutputStream out)
	{
		try
		{
			Properties p = new Properties();
			for (Field field : getClass().getDeclaredFields())
			{

				if ((field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT)
					continue;

				String value = null;

				Class<?> filedType = field.getType();
				if (filedType == String.class)
				{
					value = (String) field.get(this);
				}
				if (filedType == int.class)
				{
					value = field.get(this).toString();
				}
				if (filedType == float.class)
				{
					value = field.get(this).toString();
				}
				if (filedType == boolean.class)
				{
					value = field.get(this).toString();
				}
				if (filedType == InetSocketAddress.class)
				{
					value = field.get(this).toString();
				}
				if (filedType == File.class)
				{
					value = field.get(this).toString();
				}
				if (value != null)
					p.setProperty(field.getName(), value);
			}
			p.store(out, "");
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void load(InputStream inStream)
	{
		try
		{
			Properties p = new Properties();
			p.load(inStream);
			for (Field field : getClass().getDeclaredFields())
			{
				String value = p.getProperty(field.getName());
				if ((field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT)
					continue;
				if (value == null)
					continue;

				Class<?> filedType = field.getType();
				if (filedType == String.class)
				{
					field.set(this, value);
				}
				if (filedType == int.class)
				{
					field.set(this, Integer.parseInt(value));
				}
				if (filedType == float.class)
				{
					field.set(this, Float.parseFloat(value));
				}
				if (filedType == boolean.class)
				{
					field.set(this, Boolean.parseBoolean(value));
				}
				if (filedType == InetSocketAddress.class)
				{
					if (value.contains(":"))
					{
						String[] split = value.split(":");
						if (split.length != 2)
							throw new IllegalArgumentException(
									"IP Adresse Kann nicht gephrast werden, es werden nur ipv4 Unterstützt.");
						InetSocketAddress inetSocketAddress = new InetSocketAddress(
								split[0], Integer.parseInt(split[1]));
						field.set(this, inetSocketAddress);
					} else
					{
						InetSocketAddress inetSocketAddress = new InetSocketAddress(
								value, 0);
						field.set(this, inetSocketAddress);
					}

				}
				if (filedType == File.class)
				{
					field.set(this, new File(value));
				}
			}
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void parseCommandLine(String[] args)
	{
		try
		{
			for (int i = 0; i < args.length; ++i)
			{
				if (!args[i].startsWith("--"))
					continue;

				Field field = this.commandMap.get(args[i].substring(2));
				++i;
				if (field != null)
				{
					Class<?> filedType = field.getType();
					if (filedType == String.class)
					{
						field.set(this, args[i]);
					}
					if (filedType == int.class)
					{
						field.set(this, Integer.parseInt(args[i]));
					}
					if (filedType == float.class)
					{
						field.set(this, Float.parseFloat(args[i]));
					}
					if (filedType == boolean.class)
					{
						--i;
						field.set(this, true);
					}
					if (filedType == InetSocketAddress.class)
					{
						if (args[i].contains(":"))
						{
							String[] split = args[i].split(":");
							if (split.length != 2)
								throw new IllegalArgumentException(
										"IP Adresse Kann nicht gephrast werden, es werden nur ipv4 Unterstützt.");
							InetSocketAddress inetSocketAddress = new InetSocketAddress(
									split[0], Integer.parseInt(split[1]));
							field.set(this, inetSocketAddress);
						} else
						{
							InetSocketAddress inetSocketAddress = new InetSocketAddress(
									args[i], 0);
							field.set(this, inetSocketAddress);
						}
					}
					if (filedType == File.class)
					{
						field.set(this, new File(args[i]));
					}
				}

			}
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void showEditor(String strTitle)
	{

		try
		{
			JFrame f = new JFrame(strTitle);

			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JPanel panel = new JPanel();
			BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
			panel.setLayout(boxLayout);
			f.add(new JScrollPane(panel));

			for (final Field field : getClass().getDeclaredFields())
			{
				Object value = field.get(this);
				JLabel jLabel = new JLabel(field.getName());
				panel.add(jLabel);

				Class<?> filedType = field.getType();
				if (filedType == String.class)
				{

					final JTextField jTextField = new JTextField((String) value);
					jTextField.addActionListener(new ActionListener()
					{

						@Override
						public void actionPerformed(ActionEvent e)
						{
							try
							{
								field.set(Configuration.this,
										jTextField.getText());
							} catch (IllegalArgumentException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					panel.add(jTextField);
				}
				if (filedType == int.class)
				{
					SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(
							((Integer) value).intValue(), Integer.MIN_VALUE,
							Integer.MAX_VALUE, 1);
					final JSpinner jSpinner = new JSpinner(spinnerNumberModel);
					panel.add(jSpinner);
					jSpinner.setPreferredSize(new Dimension(60, jSpinner
							.getPreferredSize().height));
					jSpinner.addChangeListener(new ChangeListener()
					{

						@Override
						public void stateChanged(ChangeEvent e)
						{
							try
							{
								field.set(Configuration.this,
										((Integer) jSpinner.getValue())
												.intValue());
							} catch (IllegalArgumentException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					});
				}
				if (filedType == float.class)
				{
					System.err.println(value);
					System.err.println(((Float) value).doubleValue());
					SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(
							 ((Float) value).doubleValue(),
							-Double.MAX_VALUE, Double.MAX_VALUE, 1);
					final JSpinner jSpinner = new JSpinner(spinnerNumberModel);
					panel.add(jSpinner);
				    final JSpinner.NumberEditor editor =
				          new JSpinner.NumberEditor(jSpinner, "0.##########");
				    jSpinner.setEditor(editor);
					jSpinner.setPreferredSize(new Dimension(60, jSpinner
							.getPreferredSize().height));
					jSpinner.addChangeListener(new ChangeListener()
					{

						@Override
						public void stateChanged(ChangeEvent e)
						{
							try
							{
								field.set(Configuration.this,
										((Double) jSpinner.getValue())
												.floatValue());
							} catch (IllegalArgumentException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					});
				}
				if (filedType == boolean.class)
				{
					final JCheckBox jCheckBox = new JCheckBox();
					Boolean b = (Boolean) field.get(this);
					jCheckBox.setSelected(b);
					jCheckBox.addActionListener(new ActionListener()
					{

						@Override
						public void actionPerformed(ActionEvent e)
						{
							try
							{
								field.set(Configuration.this,
										(jCheckBox.isSelected()));
							} catch (IllegalArgumentException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
				}
				// if (filedType == InetSocketAddress.class)
				// {
				// value = field.get(this).toString();
				// }
				// if (filedType == File.class)
				// {
				// value = field.get(this).toString();
				// }
			}

			f.pack();
			f.setVisible(true);
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
