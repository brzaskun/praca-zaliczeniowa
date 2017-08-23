<div class="wrap accordion-slider-admin plugin-settings">
	<h2><?php _e( 'Plugin Settings', 'accordion-slider-lite' ); ?></h2>

	<form action="" method="post">
        <?php wp_nonce_field( 'plugin-settings-update', 'plugin-settings-nonce' ); ?>
        
        <table>
            <tr>
                <td>
                    <label for="load-stylesheets"><?php echo $plugin_settings['load_stylesheets']['label']; ?></label>
                </td>
                <td>
                    <select id="load-stylesheets" name="load_stylesheets">
                        <?php
                            foreach ( $plugin_settings['load_stylesheets']['available_values'] as $value_name => $value_label ) {
                                $selected = $value_name === $load_stylesheets ? ' selected="selected"' : '';
                                echo '<option value="' . $value_name . '"' . $selected . '>' . $value_label . '</option>';
                            }
                        ?>
                    </select>
                 </td>
                <td>
                    <?php echo $plugin_settings['load_stylesheets']['description']; ?>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="cache-expiry-interval"><?php echo $plugin_settings['cache_expiry_interval']['label']; ?></label>
                </td>
                <td>
                    <input type="text" id="cache-expiry-interval" name="cache_expiry_interval" value="<?php echo $cache_expiry_interval; ?>"><span>hours</span>
                </td>
                <td>
                    <?php echo $plugin_settings['cache_expiry_interval']['description']; ?>
                    <a class="button-secondary clear-all-cache" data-nonce="<?php echo wp_create_nonce( 'clear-all-cache' ); ?>"><?php _e( 'Clear all cache now', 'accordion-slider-lite' ); ?></a>
                    <span class="spinner clear-cache-spinner"></span>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="access"><?php echo $plugin_settings['access']['label']; ?></label>
                </td>
                <td>
                    <select id="access" name="access">
                        <?php
                            foreach ( $plugin_settings['access']['available_values'] as $value_name => $value_label ) {
                                $selected = $value_name === $access ? ' selected="selected"' : '';
                                echo '<option value="' . $value_name . '"' . $selected . '>' . $value_label . '</option>';
                            }
                        ?>
                    </select>
                 </td>
                <td>
                    <?php echo $plugin_settings['access']['description']; ?>
                </td>
            </tr>
        </table>

    	<input type="submit" name="plugin_settings_update" class="button-primary" value="Update Settings" />
	</form>
</div>