import { BaseEntity } from './../../shared';

export const enum Statut {
    'GO',
    'NOGO',
    'STANDBY',
    'INPROGRESS'
}

export class Etat implements BaseEntity {
    constructor(
        public id?: number,
        public dateDemarrage?: any,
        public dateDebutInterco?: any,
        public libelleStatut?: Statut,
        public placement?: BaseEntity,
    ) {
    }
}
