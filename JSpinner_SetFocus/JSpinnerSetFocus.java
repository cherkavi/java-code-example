        this.field_spinner=new JSpinner();
        this.field_spinner.setModel(new SpinnerNumberModel(1,1,this.field_max_quantity,1));
        JSpinner.NumberEditor number_editor = new JSpinner.NumberEditor(this.field_spinner, "###");
        this.field_spinner.setEditor(number_editor);
        ((JSpinner.NumberEditor )field_spinner.getEditor ()).getTextField().addFocusListener(new FocusAdapter() {
        	public void focusGained(FocusEvent e) {
        	if (e.getSource() instanceof JTextComponent) {
        		final JTextComponent textComponent=((JTextComponent)e.getSource());
        		SwingUtilities.invokeLater(new Runnable(){
        			public void run() {
        				textComponent.selectAll();
        			}
        		});
        	}
        }
        });
