import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/golf-bag">
        <Translate contentKey="global.menu.entities.golfBag" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/club-stats">
        <Translate contentKey="global.menu.entities.clubStats" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/clubs">
        <Translate contentKey="global.menu.entities.clubs" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/comment">
        <Translate contentKey="global.menu.entities.comment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/attachment">
        <Translate contentKey="global.menu.entities.attachment" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
