import { BaseEntity } from './../../shared';

export class Consultant implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public cjm?: number,
        public tjm?: number,
        public dateIntegration?: any,
        public placement?: BaseEntity,
    ) {
    }
}
