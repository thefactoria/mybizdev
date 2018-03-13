import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MybizdevConsultantModule } from './consultant/consultant.module';
import { MybizdevPlacementModule } from './placement/placement.module';
import { MybizdevEtatModule } from './etat/etat.module';
import { MybizdevBizDevModule } from './biz-dev/biz-dev.module';
import { MybizdevEquipeModule } from './equipe/equipe.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MybizdevConsultantModule,
        MybizdevPlacementModule,
        MybizdevEtatModule,
        MybizdevBizDevModule,
        MybizdevEquipeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MybizdevEntityModule {}
