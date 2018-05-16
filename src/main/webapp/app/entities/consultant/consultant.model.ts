import { BaseEntity } from './../../shared';

export class Consultant implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public cjm?: number,
        public tjMin?: number,
        public inMission?: boolean,
        public dateDebutInterco?: any,
        public placements?: BaseEntity[],
    ) {
    }
}
