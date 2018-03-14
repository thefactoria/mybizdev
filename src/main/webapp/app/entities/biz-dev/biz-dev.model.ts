import { BaseEntity } from './../../shared';

export class BizDev implements BaseEntity {
    constructor(
        public id?: number,
        public surnom?: string,
        public bizDevPlacements?: BaseEntity[],
        public equipe?: BaseEntity,
    ) {
    }
}
