package org.firstinspires.ftc.teamcode.teleop;

import android.widget.GridLayout;

import java.util.concurrent.TimeUnit;

public class IntakeOuttake {
    Claw claw;
    DepositHorizontalSlides deposit_horizontal_slides;
    HorizontalSlides horizontal_slides;
    public Intake intake;
    VerticalSlides vertical_slides;
    Sensors sensors;
    public Instructions instruction;
    public SpecificInstructions specificInstruction;
    public SpecificInstructions previousSpecificInstruction;
    private long previous_action = System.currentTimeMillis();
    private double waitTime = 1000;

    public IntakeOuttake(Sensors sensors, Claw claw, DepositHorizontalSlides deposit_horizontal_slides, HorizontalSlides horizontal_slides, Intake intake, VerticalSlides vertical_slides) {
        this.sensors = sensors;
        this.claw = claw;
        this.deposit_horizontal_slides = deposit_horizontal_slides;
        this.horizontal_slides = horizontal_slides;
        this.intake = intake;
        this.vertical_slides = vertical_slides;

        instruction = Instructions.CLOSED;
        specificInstruction = SpecificInstructions.CLOSED;
    }

    public void reset(SpecificInstructions next) {
        previous_action = System.currentTimeMillis();
        waitTime = specificInstruction.time();
        specificInstruction = next;
    }

    public void reset(double time, SpecificInstructions next) {
        previous_action = System.currentTimeMillis();
        waitTime = time;
        specificInstruction = next;
    }

    public void update() {
        switch (instruction) {
            case INITIAL_CLOSED:
                switch (specificInstruction) {
                    case EXTEND_VERTICAL:
                        intake.dropdown_up();
                        vertical_slides.vertical_offset = 0;
                        if(VerticalSlides.verticalSlides.getCurrentPosition() > -900) {
                            VerticalSlides.go_to_closed_reset();
                            reset(SpecificInstructions.RETRACT_HORIZONTAL);
                        }
                        else{
                            reset(SpecificInstructions.RETRACT_HORIZONTAL);
                        }
                        break;
                    case RETRACT_HORIZONTAL:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            HorizontalSlides.retract();
                            reset(SpecificInstructions.CLOSE_CLAWS);
                        }
                        break;
                    case CLOSE_CLAWS:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            Claw.close_left_claw();
                            Claw.close_right_claw();
                            reset(SpecificInstructions.RETRACT_DEPOSIT_HORIZONTAL);
                        }
                        break;
                    case RETRACT_DEPOSIT_HORIZONTAL:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            DepositHorizontalSlides.intake();
                            reset(SpecificInstructions.TILT_CLAWS);
                        }
                        break;
                    case TILT_CLAWS:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            Claw.intake_tilt();
                            reset(SpecificInstructions.OPEN_CLAWS);
                        }
                        break;
                    case OPEN_CLAWS:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            Claw.open_left_claw();
                            Claw.open_right_claw();
                            reset(SpecificInstructions.STOP_ROLLERS);
                        }
                        break;
                    case STOP_ROLLERS:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            Intake.stop();
                            reset(SpecificInstructions.RETRACT_VERTICAL);
                        }
                        break;
                    case RETRACT_VERTICAL:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            VerticalSlides.go_to_ground();
                        }
                        if (VerticalSlides.verticalSlides.getCurrentPosition() >= -10) {
                            Claw.left_claw_tilt_position = 0.315;
                            Claw.right_claw_tilt_position = 0.175;
                        }
                        break;
                }
                break;

            case CLOSED:
                switch (specificInstruction) {
                    case DROP_PIXEL:
                        Claw.open_left_claw();
                        Claw.open_right_claw();
                        reset(SpecificInstructions.EXTEND_VERTICAL);
                        break;
                    case EXTEND_VERTICAL:
                        vertical_slides.vertical_offset = 0;
                        if (VerticalSlides.verticalSlides.getCurrentPosition() > -900) {
                            VerticalSlides.go_to_closed_reset();
                            reset(SpecificInstructions.RETRACT_HORIZONTAL);
                        }
                        else {
                            reset(SpecificInstructions.RETRACT_HORIZONTAL);
                        }
                        break;
                    case RETRACT_HORIZONTAL:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            HorizontalSlides.retract();
                            reset(SpecificInstructions.CLOSE_CLAWS);
                        }
                        break;
                    case CLOSE_CLAWS:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            Claw.close_left_claw();
                            Claw.close_right_claw();
                            reset(SpecificInstructions.RETRACT_DEPOSIT_HORIZONTAL);
                        }
                        break;
                    case RETRACT_DEPOSIT_HORIZONTAL:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            DepositHorizontalSlides.intake();
                            reset(SpecificInstructions.TILT_CLAWS);
                        }
                        break;
                    case TILT_CLAWS:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            Claw.intake_tilt();
                            reset(SpecificInstructions.OPEN_CLAWS);
                        }
                        break;
                    case OPEN_CLAWS:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            Claw.open_left_claw();
                            Claw.open_right_claw();
                            reset(SpecificInstructions.RETRACT_VERTICAL);
                        }
                        break;
                    case RETRACT_VERTICAL:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            VerticalSlides.go_to_ground();
                        }
                        if (VerticalSlides.verticalSlides.getCurrentPosition() >= -10) {
                            Claw.left_claw_tilt_position = 0.3075;
                            Claw.right_claw_tilt_position = 0.17;
                            reset(SpecificInstructions.STOP_ROLLERS);
                        }
                        break;
                    case STOP_ROLLERS:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            Intake.stop();
                            reset(SpecificInstructions.RETRACT_VERTICAL);
                        }
                        break;
                }
                break;

            case CLOSED_INTAKE:
                switch (specificInstruction) {
                    case DROPDOWN_DOWN:
                        vertical_slides.vertical_offset = 0;
                        intake.dropdown_down();
                        reset(SpecificInstructions.SPIN_ROLLERS);
                        break;
                    case SPIN_ROLLERS:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            intake.intake();
                        }
                        break;
                }
                break;

            case OPEN_INTAKE:
                switch (specificInstruction) {
                    case EXTEND_VERTICAL:
                        vertical_slides.vertical_offset = 0;
                        vertical_slides.go_to_low();
                        reset(SpecificInstructions.EXTEND_HORIZONTAL);
                        break;
                    case EXTEND_HORIZONTAL:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            HorizontalSlides.extend();
                            reset(SpecificInstructions.DROPDOWN_DOWN);
                            break;
                        }
                    case DROPDOWN_DOWN:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            Intake.dropdown_single();
                        }
                        break;

                    case SINGLE_PIXEL:
                        Intake.dropdown_down();
                        Intake.setDropdown();
                        long previous_time = System.currentTimeMillis();
                        try {
                            Thread.sleep(1100);
                        } catch (InterruptedException e) {
                            // Handle interruption exception if needed
                            e.printStackTrace();
                        }
                        if (System.currentTimeMillis() - previous_time > 1000) {
                            Intake.dropdown_single();
                            Intake.setDropdown();
                        }
                        break;


                }
                break;

            case OPEN_LEFT_CLAW:
                switch (specificInstruction) {
                    case OPEN_LEFT_CLAW:
                        Claw.open_left_claw();
                        break;
                }
                break;

            case OPEN_RIGHT_CLAW:
                switch (specificInstruction) {
                    case OPEN_RIGHT_CLAW:
                        Claw.open_right_claw();
                        break;
                }
                break;

            case EXTEND_VERTICAL_FOR_INTAKE:
                switch (specificInstruction) {
                    case EXTEND_VERTICAL:
                        vertical_slides.vertical_offset = 0;
                        vertical_slides.go_to_low();
                        intake.dropdown_single();
                        intake.setDropdown();
                        break;
                }
                break;





            case STACK_INTAKE:
                switch (specificInstruction) {
                    case EXTEND_VERTICAL:
                        vertical_slides.vertical_offset = 0;
                        vertical_slides.go_to_low();
                        intake.dropdown_down(0.4);
                        break;
                }
                break;


            case DEPOSIT:
                switch (specificInstruction) {
                    case RETRACT_VERTICAL:
                        vertical_slides.vertical_offset = 0;
                        vertical_slides.go_to_ground();
                        reset(specificInstruction.CLOSE_CLAWS);
                        break;
                    case CLOSE_CLAWS:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            Claw.close_left_claw();
                            Claw.close_right_claw();
                            reset(SpecificInstructions.EXTEND_VERTICAL);
                        }
                        break;
                    case EXTEND_VERTICAL:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            vertical_slides.go_to_low();
                            reset(SpecificInstructions.EXTEND_DEPOSIT_HORIZONTAL);
                        }
                        break;
                    case EXTEND_DEPOSIT_HORIZONTAL:
                        if (System.currentTimeMillis() - previous_action > waitTime && vertical_slides.verticalSlides.getCurrentPosition() <= -760) {
                            deposit_horizontal_slides.deposit();
                            reset(SpecificInstructions.TILT_CLAWS);
                        }
                        break;
                    case TILT_CLAWS:
                        if (System.currentTimeMillis() - previous_action > waitTime) {
                            claw.deposit_tilt();
                        }
                        break;
                }
                break;
        }

        claw.set();
        deposit_horizontal_slides.set();
        intake.setIntake();
        intake.setDropdown();
        vertical_slides.set();
        horizontal_slides.set();
    }

    public void setInstructions(Instructions instruction) {
        this.instruction = instruction;
    }

    public void setSpecificInstruction(SpecificInstructions specificInstruction) {
        this.specificInstruction = specificInstruction;
    }

    public enum Instructions {
        INITIAL_CLOSED,
        CLOSED,
        CLOSED_INTAKE,
        OPEN_INTAKE,
        DEPOSIT,
        OPEN_LEFT_CLAW,
        OPEN_RIGHT_CLAW,
        EXTEND_VERTICAL_FOR_INTAKE,
        STACK_INTAKE
    }

    public enum SpecificInstructions {
        CLOSED(1000),
        SPIN_ROLLERS(1000),
        REVERSE_ROLLERS(1000),
        STOP_ROLLERS(0),
        EXTEND_HORIZONTAL(1000),
        RETRACT_HORIZONTAL(50),
        EXTEND_DEPOSIT_HORIZONTAL(1000),
        RETRACT_DEPOSIT_HORIZONTAL(200),
        CLOSE_CLAWS(500),
        TILT_CLAWS(300),
        DROPDOWN_DOWN(1000),
        DROPDOWN_UP(1000),
        OPEN_LEFT_CLAW(1000),
        OPEN_RIGHT_CLAW(1000),
        EXTEND_VERTICAL(1000),
        RETRACT_VERTICAL(750),
        OPEN_CLAWS(300),
        SINGLE_PIXEL(300),
        DROP_PIXEL(300);


        private final int executionTime;

        SpecificInstructions(int executionTime) {
            this.executionTime = executionTime;
        }

        public int time() {
            return executionTime;
        }
    }
}