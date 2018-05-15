import { Placement } from '@ng-bootstrap/ng-bootstrap';

import { BaseEntity } from '../../shared';
import { Equipe } from '../equipe/equipe.model';

export class BizDev implements BaseEntity {
    constructor(
        public id?: number,
        public surnom?: string,
        public bizDevPlacements?: Placement[],
        public equipe?: Equipe,
    ) {
    }
}
